package com.yapp.bol.group

import com.yapp.bol.auth.UserId
import com.yapp.bol.base.ARRAY
import com.yapp.bol.base.BOOLEAN
import com.yapp.bol.base.ControllerTest
import com.yapp.bol.base.ENUM
import com.yapp.bol.base.NUMBER
import com.yapp.bol.base.OBJECT
import com.yapp.bol.base.OpenApiTag
import com.yapp.bol.base.STRING
import com.yapp.bol.file.FileService
import com.yapp.bol.game.GameId
import com.yapp.bol.group.dto.CheckAccessCodeRequest
import com.yapp.bol.group.dto.CreateGroupRequest
import com.yapp.bol.group.dto.GroupMemberList
import com.yapp.bol.group.dto.GroupWithMemberCount
import com.yapp.bol.group.member.HostMember
import com.yapp.bol.group.member.MemberId
import com.yapp.bol.group.member.MemberList
import com.yapp.bol.group.member.MemberRole
import com.yapp.bol.group.member.OwnerMember
import com.yapp.bol.pagination.offset.PaginationOffsetResponse
import io.mockk.every
import io.mockk.mockk

class GroupControllerTest : ControllerTest() {
    private val groupService: GroupService = mockk()
    private val fileService: FileService = mockk()
    override val controller = GroupController(groupService, fileService)

    init {
        test("그룹 기본 이미지 가져오기") {
            every { fileService.getDefaultGroupImageUrl() } returns "http://localhost:8080/default-image"

            get("/v1/group/default-image") {}
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "group/{method-name}",
                        description = "그룹 기본 이미지 랜덤으로 가져오기",
                        tag = OpenApiTag.GROUP
                    ),
                    responseFields(
                        "url" type STRING means "기본이미지 URL",
                    )
                )
        }

        test("그룹 생성하기") {
            val request = CreateGroupRequest(
                name = "뽀글뽀글",
                description = "보겜동입니다",
                organization = "카카오",
                profileImageUrl = "https://profile.com",
                nickname = "홀든",
            )

            every {
                groupService.createGroup(any())
            } returns GroupMemberList(group = GROUP, members = MEMBER_LIST)

            post("/v1/group", request) {
                authorizationHeader(UserId(1))
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "group/{method-name}", tag = OpenApiTag.GROUP),
                    requestFields(
                        "name" type STRING means "그룹 이름",
                        "description" type STRING means "그룹 설명",
                        "organization" type STRING means "그룹 소속",
                        "profileImageUrl" type STRING means "그룹 프로필 이미지 URL" isOptional true,
                        "nickname" type STRING means "그룹장 닉네임" isOptional true,
                    ),
                    responseFields(
                        "id" type NUMBER means "그룹 ID",
                        "name" type STRING means "그룹 이름",
                        "description" type STRING means "그룹 설명",
                        "owner" type STRING means "그룹장 닉네임",
                        "organization" type STRING means "그룹 소속",
                        "profileImageUrl" type STRING means "그룹 프로필 이미지 URL",
                        "accessCode" type STRING means "그룹 접근 코드"
                    )
                )
        }

        test("그룹 리스트 가져오기") {
            val name = "뽀글뽀글"
            val pageNumber = 0
            val pageSize = 10

            every {
                groupService.searchGroup(any(), any(), any())
            } returns PaginationOffsetResponse<GroupWithMemberCount>(
                content = listOf(GROUP_WITH_MEMBER_COUNT),
                hasNext = false,
            )

            get("/v1/group") {
                queryParam("keyword", name)
                queryParam("pageNumber", pageNumber.toString())
                queryParam("pageSize", pageSize.toString())
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "group/{method-name}", tag = OpenApiTag.GROUP),
                    queryParameters(
                        "keyword" type STRING means "검색하고자 하는 텍스트, (이름/소속)을 검색합니다. (디폴트 All)" isOptional true,
                        "pageNumber" type NUMBER means "페이지 번호 (디폴트 0)" isOptional true,
                        "pageSize" type NUMBER means "페이지 크기 (디폴트 10)" isOptional true
                    ),
                    responseFields(
                        "content" type ARRAY means "그룹 목록",
                        "content[].id" type NUMBER means "그룹 ID",
                        "content[].name" type STRING means "그룹 이름",
                        "content[].description" type STRING means "그룹 설명",
                        "content[].organization" type STRING means "그룹 소속" isOptional true,
                        "content[].profileImageUrl" type STRING means "그룹 프로필 이미지 URL",
                        "content[].memberCount" type NUMBER means "그룹 멤버 수",
                        "hasNext" type BOOLEAN means "다음 페이지 존재 여부"
                    )
                )
        }

        test("리더보드 보기") {
            val groupId = GroupId(1)
            val gameId = GameId(123)

            every { groupService.getLeaderBoard(groupId, gameId) } returns listOf(
                LeaderBoardMember(
                    member = HostMember(
                        MemberId(1),
                        userId = UserId(1),
                        nickname = "난_1등",
                    ),
                    score = 100,
                    winningPercentage = 0.95,
                    matchCount = 12,
                ),
                LeaderBoardMember(
                    member = HostMember(
                        MemberId(2),
                        userId = UserId(2),
                        nickname = "게임안해",
                    ),
                    score = null,
                    winningPercentage = null,
                    matchCount = null,
                )
            )

            get("/v1/group/{groupId}/game/{gameId}", arrayOf(groupId.value, gameId.value)) {}
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "group/{method-name}",
                        description = "게임 별 리더보드 보기",
                        tag = OpenApiTag.GROUP
                    ),
                    pathParameters(
                        "groupId" type NUMBER means "그룹 ID",
                        "gameId" type NUMBER means "게임 ID",
                    ),
                    responseFields(
                        "contents" type ARRAY means "그룹 목록",
                        "contents[].id" type NUMBER means "맴버 ID",
                        "contents[].nickname" type STRING means "맴버 닉네임",
                        "contents[].rank" type NUMBER means "등수, 1부터 시작" isOptional true,
                        "contents[].winningPercentage" type NUMBER means "승률 소수로 나옴 (반올림 없음), 0~1" isOptional true,
                        "contents[].matchCount" type NUMBER means "총 플레이 횟수" isOptional true,
                    )
                )
        }

        test("그룹 가입 중 참여 코드 확인") {
            val groupId = GroupId(67L)
            val accessCode = "code00"

            every {
                groupService.checkAccessToken(groupId, accessCode)
            } returns true

            post("/v1/group/{groupId}/accessCode", CheckAccessCodeRequest(accessCode), arrayOf(groupId.value)) {
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(identifier = "group/{method-name}", tag = OpenApiTag.GROUP),
                    pathParameters(
                        "groupId" type NUMBER means "그룹 ID"
                    ),
                    requestFields(
                        "accessCode" type STRING means "엑세스 코드"
                    ),
                    responseFields(
                        "result" type BOOLEAN means "엑세스 코드 기출 여부",
                    )
                )
        }

        test("그룹 상세 정보 보기") {
            val groupId = GroupId(123L)

            every {
                groupService.getGroupWithMemberCount(any())
            } returns GROUP_WITH_MEMBER_COUNT
            every {
                groupService.getOwner(groupId)
            } returns OwnerMember(
                userId = UserId(32L),
                nickname = "닉네임",
            )

            get("/v1/group/{groupId}", arrayOf(groupId.value)) {
                authorizationHeader(UserId(1L))
            }
                .isStatus(200)
                .makeDocument(
                    DocumentInfo(
                        identifier = "group/{method-name}",
                        description = "단일 그룹 상세 정보 보기",
                        tag = OpenApiTag.GROUP,
                    ),
                    pathParameters(
                        "groupId" type NUMBER means "그룹 ID"
                    ),
                    responseFields(
                        "id" type NUMBER means "그룹 ID",
                        "name" type STRING means "그룹 이름",
                        "description" type STRING means "그룹 설명",
                        "organization" type STRING means "그룹 소속" isOptional true,
                        "profileImageUrl" type STRING means "그룹 프로필 이미지 URL",
                        "accessCode" type STRING means "그룹 참여 코드",
                        "memberCount" type NUMBER means "그룹 멤버 수",
                        "owner" type OBJECT means "그룹 Owner 정보",
                        "owner.id" type NUMBER means "Owner ID",
                        "owner.role" type ENUM(MemberRole::class) means "OWNER 로 고정",
                        "owner.nickname" type STRING means "Owner 닉네임",
                        "owner.level" type NUMBER means "주사위 등급",
                    )
                )
        }
    }

    companion object {
        val GROUP = Group(
            id = GroupId(1),
            name = "뽀글뽀글",
            description = "보겜동입니다",
            organization = "카카오",
            profileImageUrl = "https://profile.com",
            accessCode = "1A2B3C",
        )

        val MEMBER_LIST = MemberList(
            OwnerMember(
                userId = UserId(1),
                nickname = "홀든",
            )
        )

        val GROUP_WITH_MEMBER_COUNT = GroupWithMemberCount(
            group = GROUP,
            memberCount = MEMBER_LIST.getSize()
        )
    }
}

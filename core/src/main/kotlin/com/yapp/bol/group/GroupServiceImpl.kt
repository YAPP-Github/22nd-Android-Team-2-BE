package com.yapp.bol.group

import com.yapp.bol.AccessCodeNotMatchException
import com.yapp.bol.NotFoundGroupException
import com.yapp.bol.UnAuthorizationException
import com.yapp.bol.auth.UserId
import com.yapp.bol.game.GameId
import com.yapp.bol.group.dto.AddGuestDto
import com.yapp.bol.group.dto.CreateGroupDto
import com.yapp.bol.group.dto.GroupMemberList
import com.yapp.bol.group.dto.GroupWithMemberCount
import com.yapp.bol.group.dto.JoinGroupDto
import com.yapp.bol.group.member.MemberQueryRepository
import com.yapp.bol.group.member.MemberService
import com.yapp.bol.group.member.OwnerMember
import com.yapp.bol.pagination.offset.PaginationOffsetResponse
import org.springframework.stereotype.Service

@Service
internal class GroupServiceImpl(
    private val groupQueryRepository: GroupQueryRepository,
    private val groupCommandRepository: GroupCommandRepository,
    private val memberService: MemberService,
    private val memberQueryRepository: MemberQueryRepository,
) : GroupService {

    override fun createGroup(
        createGroupDto: CreateGroupDto
    ): GroupMemberList {
        val group = Group(
            name = createGroupDto.name,
            description = createGroupDto.description,
            organization = createGroupDto.organization,
            profileImageUrl = createGroupDto.profileImageUrl ?: Group.DEFAULT_PROFILE_IMAGE_URL,
        )

        val owner = OwnerMember(
            userId = createGroupDto.ownerId,
            nickname = createGroupDto.nickname ?: "기본 닉네임" // TODO: User Service
        )

        return groupCommandRepository.createGroup(group, owner)
    }

    override fun joinGroup(request: JoinGroupDto) {
        val group = groupQueryRepository.findById(request.groupId) ?: throw NotFoundGroupException

        if (group.accessCode != request.accessCode) throw AccessCodeNotMatchException

        memberService.createHostMember(request.userId, request.groupId, request.nickname)
    }

    override fun searchGroup(
        keyword: String?,
        pageNumber: Int,
        pageSize: Int
    ): PaginationOffsetResponse<GroupWithMemberCount> {
        val groups = groupQueryRepository.search(
            keyword = keyword,
            pageNumber = pageNumber,
            pageSize = pageSize
        )

        val groupWithMemberCount = groups.content.map { group ->
            val memberCount = memberQueryRepository.getCount(group.id).toInt()

            GroupWithMemberCount(group, memberCount)
        }

        return PaginationOffsetResponse(groupWithMemberCount, groups.hasNext)
    }

    override fun addGuest(request: AddGuestDto) {
        memberQueryRepository.findByGroupIdAndUserId(request.groupId, request.requestUserId)
            ?: throw UnAuthorizationException()

        memberService.createGuestMember(request.groupId, request.nickname)
    }

    override fun getLeaderBoard(groupId: GroupId, gameId: GameId): List<LeaderBoardMember> {
        return groupQueryRepository.getLeaderBoardList(groupId, gameId)
    }

    override fun getGroupsByUserId(userId: UserId): List<Group> {
        return groupQueryRepository.getGroupsByUserId(userId)
    }

    override fun checkAccessToken(groupId: GroupId, accessToken: String): Boolean {
        val group = groupQueryRepository.findById(groupId) ?: throw NotFoundGroupException

        return group.accessCode == accessToken
    }

    override fun getGroupWithMemberCount(groupId: GroupId): GroupWithMemberCount {
        val group = groupQueryRepository.findById(groupId) ?: throw NotFoundGroupException
        val memberCount = memberQueryRepository.getCount(groupId)
        return GroupWithMemberCount(group, memberCount)
    }

    override fun getOwner(groupId: GroupId): OwnerMember {
        return memberQueryRepository.findOwner(groupId)
    }
}

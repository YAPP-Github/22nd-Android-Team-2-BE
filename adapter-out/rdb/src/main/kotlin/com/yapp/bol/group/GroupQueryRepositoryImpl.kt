package com.yapp.bol.group

import com.yapp.bol.auth.UserId
import com.yapp.bol.game.GameId
import com.yapp.bol.group.member.MemberEntity
import com.yapp.bol.group.member.MemberRepository
import com.yapp.bol.group.member.toDomain
import com.yapp.bol.pagination.offset.PaginationOffsetResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Slice
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
internal class GroupQueryRepositoryImpl(
    private val groupRepository: GroupRepository,
    private val memberRepository: MemberRepository,
) : GroupQueryRepository {
    override fun findById(id: GroupId): Group? {
        return groupRepository.findByIdOrNull(id.value)?.toDomain()
    }

    override fun search(name: String?, pageNumber: Int, pageSize: Int): PaginationOffsetResponse<Group> {
        val pageable = PageRequest.of(pageNumber, pageSize)

        if (name.isNullOrEmpty()) {
            val groups: Slice<GroupEntity> = groupRepository.findAll(pageable)

            return toCursor(groups)
        }

        val groups: Slice<GroupEntity> =
            groupRepository.findByNameLikeOrOrganizationLike("%$name%", "%$name%", pageable)

        return toCursor(groups)
    }

    private fun toCursor(slice: Slice<GroupEntity>): PaginationOffsetResponse<Group> {
        val content: List<Group> = slice.content.map(GroupEntity::toDomain)

        return PaginationOffsetResponse(content, slice.hasNext())
    }

    override fun getLeaderBoardList(groupId: GroupId, gameId: GameId): List<LeaderBoardMember> {
        return memberRepository.findWithGameMember(groupId.value).map {
            it.toLeaderBoardDomain(gameId)
        }
    }

    private fun MemberEntity.toLeaderBoardDomain(gameId: GameId): LeaderBoardMember {
        val gameMember = this.gameMembers.firstOrNull { it.gameId == gameId.value }

        return LeaderBoardMember(
            member = this.toDomain(),
            score = gameMember?.finalScore,
            winningPercentage = gameMember?.winningPercentage,
            matchCount = gameMember?.matchCount,
        )
    }

    override fun getGroupsByUserId(userId: UserId): List<Group> =
        groupRepository.findByUserId(userId.value).map { it.toDomain() }
}

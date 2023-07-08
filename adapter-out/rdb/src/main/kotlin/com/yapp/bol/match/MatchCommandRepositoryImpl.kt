package com.yapp.bol.match

import com.yapp.bol.group.member.MemberId
import com.yapp.bol.match.dto.MatchWithMatchMemberList
import com.yapp.bol.match.member.MatchMember
import com.yapp.bol.match.member.MatchMemberRepository
import com.yapp.bol.match.member.toDomain
import com.yapp.bol.match.member.toEntity
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
internal class MatchCommandRepositoryImpl(
    private val matchRepository: MatchRepository,
    private val matchMemberRepository: MatchMemberRepository,
) : MatchCommandRepository {
    @Transactional
    override fun createMatch(matchWithMatchMemberList: MatchWithMatchMemberList): MatchWithMatchMemberList {
        // TODO: 점수 처리 필요
        val match = matchRepository.save(matchWithMatchMemberList.match.toEntity()).toDomain()

        val matchMembers = matchWithMatchMemberList.matchMembers
            .map { matchMembers ->
                saveMatchMember(match.id, matchMembers, matchMembers.memberId)
            }.map { matchMemberEntity ->
                matchMemberEntity.toDomain()
            }

        return MatchWithMatchMemberList(
            match = match,
            matchMembers = matchMembers
        )
    }

    private fun saveMatchMember(matchId: MatchId, matchMember: MatchMember, memberId: MemberId) =
        matchMemberRepository.save(
            matchMember.toEntity(
                matchId = matchId,
                memberId = memberId
            )
        )
}

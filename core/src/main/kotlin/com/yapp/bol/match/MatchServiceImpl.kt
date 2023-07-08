package com.yapp.bol.match

import com.yapp.bol.game.GameService
import com.yapp.bol.game.member.GameMemberQueryRepository
import com.yapp.bol.match.dto.CreateMatchDto
import com.yapp.bol.match.dto.MatchWithMatchMemberList
import com.yapp.bol.match.dto.toDomain
import com.yapp.bol.match.member.MatchMember
import org.springframework.stereotype.Service

@Service
class MatchServiceImpl(
    private val gameService: GameService,
    private val matchQueryRepository: GameMemberQueryRepository,
    private val matchCommandRepository: MatchCommandRepository
) : MatchService {
    override fun createMatch(createMatchDto: CreateMatchDto) {
        // match

        // get final scores
        val finalScores: List<Int> = createMatchDto.matchMembers.map { matchMemberDto ->
            matchQueryRepository.findGameMemberByMemberId(matchMemberDto.memberId.value)
                .finalScore
        }

        // match member
        val matchMembers: List<MatchMember> = finalScores.zip(createMatchDto.matchMembers)
            .map { (finalScore, matchMemberDto) ->
                matchMemberDto.toDomain(finalScore)
            }

        val matchWithMatchMembers: MatchWithMatchMemberList = createMatchDto.toDomain(matchMembers)

        // save match
        matchCommandRepository.createMatch(matchWithMatchMembers)

        // TODO: update score (GameMember)
    }
}

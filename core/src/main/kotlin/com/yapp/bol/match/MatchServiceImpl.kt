package com.yapp.bol.match

import com.yapp.bol.game.GameService
import com.yapp.bol.match.dto.CreateMatchDto
import org.springframework.stereotype.Service

@Service
class MatchServiceImpl(
    private val gameService: GameService,
    private val matchCommandRepository: MatchCommandRepository
) : MatchService {
    override fun createMatch(createMatchDto: CreateMatchDto) {
        // match

        // match member

        // game member

        // final score -> previous score

        // TODO: find previous(current) scores

//        val matchWithMatchMembers: MatchWithMatchMemberList = createMatchDto.toDomain(
//            createMatchDto.matchMembers.map {
//                it.toDomain()
//            }
//        )

        // save match
//        matchCommandRepository.createMatch(matchWithMatchMembers)

        // TODO: update score (GameMember)
    }
}

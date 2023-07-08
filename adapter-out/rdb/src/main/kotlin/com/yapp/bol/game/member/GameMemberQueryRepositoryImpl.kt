package com.yapp.bol.game.member

class GameMemberQueryRepositoryImpl(
    private val gameMemberRepository: GameMemberRepository
) : GameMemberQueryRepository {
    override fun findGameMemberByMemberId(memberId: Long): GameMember {
        return gameMemberRepository.findByMemberId(memberId).toDomain()
    }
}

package com.yapp.bol.game.member

interface GameMemberQueryRepository {
    fun findGameMemberByMemberId(gameId: Long): GameMember
}

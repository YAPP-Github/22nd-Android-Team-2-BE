package com.yapp.bol.game.member

import com.yapp.bol.game.GameId
import com.yapp.bol.group.member.MemberId
import com.yapp.bol.season.Season

@JvmInline
value class GameMemberId(val value: Long)

class GameMember(
    val id: GameMemberId,
    val gameId: GameId,
    val memberId: MemberId,
    val finalScore: Int,
    val season: Season,
    val matchCount: Int,
    val winningPercentage: Double
)

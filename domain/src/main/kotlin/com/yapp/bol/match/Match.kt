package com.yapp.bol.match

import com.yapp.bol.game.GameId
import com.yapp.bol.group.GroupId
import java.time.LocalDateTime

@JvmInline
value class MatchId(val value: Long)

class Match(
    val id: MatchId = MatchId(0),
    val gameId: GameId,
    val groupId: GroupId,
    val matchedDate: LocalDateTime,
    val memberCount: Int
)

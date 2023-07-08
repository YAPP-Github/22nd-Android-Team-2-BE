package com.yapp.bol.match.dto

import com.yapp.bol.game.GameId
import com.yapp.bol.group.GroupId
import com.yapp.bol.group.member.MemberId
import java.time.LocalDateTime

data class MatchMemberRequest(
    val memberId: Long,
    val score: Int,
    val ranking: Int,
)

data class CreateMatchRequest(
    val gameId: Long,
    val groupId: Long,
    val matchedDate: LocalDateTime,
    val matchMembers: List<MatchMemberRequest>
)

internal fun MatchMemberRequest.toDto() = CreateMatchMemberDto(
    memberId = MemberId(this.memberId),
    score = this.score,
    ranking = this.ranking,
)

internal fun CreateMatchRequest.toDto() = CreateMatchDto(
    gameId = GameId(this.gameId),
    groupId = GroupId(this.groupId),
    matchedDate = this.matchedDate,
    matchMembers = this.matchMembers.map { it.toDto() }
)

package com.yapp.bol.match.dto

import com.yapp.bol.game.GameId
import com.yapp.bol.group.GroupId
import com.yapp.bol.group.member.MemberId
import com.yapp.bol.match.Match
import com.yapp.bol.match.MatchId
import com.yapp.bol.match.member.MatchMember
import com.yapp.bol.match.member.MatchMemberId
import java.time.LocalDateTime

data class CreateMatchMemberDto(
    val memberId: MemberId,
    val score: Int,
    val ranking: Int,
)

data class CreateMatchDto(
    val gameId: GameId,
    val groupId: GroupId,
    val matchedDate: LocalDateTime,
    val matchMembers: List<CreateMatchMemberDto>
)

fun CreateMatchMemberDto.toDomain(
    previousScore: Int,
): MatchMember = MatchMember(
    id = MatchMemberId(0),
    matchId = MatchId(0),
    memberId = this.memberId,
    score = this.score,
    ranking = this.ranking,
    previousScore = previousScore,
)

fun CreateMatchDto.toDomain(matchMembers: List<MatchMember>): MatchWithMatchMemberList = MatchWithMatchMemberList(
    match = Match(
        id = MatchId(0),
        gameId = this.gameId,
        groupId = this.groupId,
        matchedDate = this.matchedDate,
        memberCount = this.matchMembers.size,
    ),
    matchMembers = matchMembers
)

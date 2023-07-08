package com.yapp.bol.match.member

import com.yapp.bol.group.member.MemberId
import com.yapp.bol.match.MatchId

@JvmInline
value class MatchMemberId(val value: Long)

class MatchMember(
    val id: MatchMemberId,
    val matchId: MatchId,
    val memberId: MemberId,
    val score: Int,
    val ranking: Int,
    val previousScore: Int
)

package com.yapp.bol.match.dto

import com.yapp.bol.match.Match
import com.yapp.bol.match.member.MatchMember

data class MatchWithMatchMemberList(
    val match: Match,
    val matchMembers: List<MatchMember>
)

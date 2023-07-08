package com.yapp.bol.match

import com.yapp.bol.match.dto.MatchWithMatchMemberList

interface MatchCommandRepository {
    fun createMatch(matchWithMatchMemberList: MatchWithMatchMemberList): MatchWithMatchMemberList
}

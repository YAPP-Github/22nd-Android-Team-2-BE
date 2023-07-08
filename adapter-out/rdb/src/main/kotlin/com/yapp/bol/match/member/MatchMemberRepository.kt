package com.yapp.bol.match.member

import org.springframework.data.jpa.repository.JpaRepository

internal interface MatchMemberRepository : JpaRepository<MatchMemberEntity, Long>

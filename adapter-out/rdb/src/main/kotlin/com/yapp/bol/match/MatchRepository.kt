package com.yapp.bol.match

import org.springframework.data.jpa.repository.JpaRepository

internal interface MatchRepository : JpaRepository<MatchEntity, Long>

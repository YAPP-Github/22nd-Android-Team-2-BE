package com.yapp.bol.season

import org.springframework.data.jpa.repository.JpaRepository

internal interface SeasonRepository : JpaRepository<SeasonEntity, Long>

package com.yapp.bol.game.member

import org.springframework.data.jpa.repository.JpaRepository

interface GameMemberRepository : JpaRepository<GameMemberEntity, Long>

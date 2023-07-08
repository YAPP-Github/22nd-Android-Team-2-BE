package com.yapp.bol.game

import com.yapp.bol.match.MatchEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface GameRepository : JpaRepository<MatchEntity, Long> {
    @Query("FROM GameEntity e JOIN FETCH e.img")
    fun getAll(): List<GameEntity>

    fun findByGame(game: GameEntity)
}

package com.yapp.bol.game.dto

import com.yapp.bol.game.Game
import com.yapp.bol.game.GameId

data class GameResponse(
    val id: GameId,
    val name: String,
    val minMember: Int,
    val maxMember: Int,
    val img: String,
)

fun Game.toResponse(): GameResponse = GameResponse(
    id = this.id,
    name = this.name,
    minMember = this.minMember,
    maxMember = this.maxMember,
    img = this.img,
)

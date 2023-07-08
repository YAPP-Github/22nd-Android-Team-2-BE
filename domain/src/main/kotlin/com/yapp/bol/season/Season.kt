package com.yapp.bol.season

@JvmInline
value class SeasonId(val value: Long)

class Season(
    val id: SeasonId,
)

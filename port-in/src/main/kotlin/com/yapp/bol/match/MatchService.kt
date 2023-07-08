package com.yapp.bol.match

import com.yapp.bol.match.dto.CreateMatchDto

interface MatchService {
    fun createMatch(createMatchDto: CreateMatchDto)
}

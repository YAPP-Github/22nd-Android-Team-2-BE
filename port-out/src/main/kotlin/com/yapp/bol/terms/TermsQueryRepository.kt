package com.yapp.bol.terms

import com.yapp.bol.auth.UserId

interface TermsQueryRepository {
    fun getSavedTermsByUserId(userId: UserId): List<TermsAgreeInfo>
}

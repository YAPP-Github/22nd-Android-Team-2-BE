package com.yapp.bol.terms

import com.yapp.bol.auth.UserId

interface TermsCommandRepository {
    fun saveTermsAgreeInfo(userId: UserId, termsCode: List<TermsAgreeInfo>)
}

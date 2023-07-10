package com.yapp.bol.terms

import com.yapp.bol.auth.UserId

interface TermsService {
    fun getTermsList(): List<Terms>

    fun agreeTerms(userId: UserId, termsCode: TermsCode)
}

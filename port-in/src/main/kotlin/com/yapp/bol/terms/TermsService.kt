package com.yapp.bol.terms

import com.yapp.bol.auth.UserId

interface TermsService {
    fun getNeedTermsAgreeList(userId: UserId): List<TermsCode>

    fun agreeTerms(userId: UserId, termsInfoList: List<TermsAgreeInfo>)
}

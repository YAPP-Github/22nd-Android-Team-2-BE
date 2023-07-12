package com.yapp.bol.terms.dto

import com.yapp.bol.terms.TermsCode

data class AgreeTermsRequest(
    val agree: List<TermsCode>?,
    val disagree: List<TermsCode>?,
)

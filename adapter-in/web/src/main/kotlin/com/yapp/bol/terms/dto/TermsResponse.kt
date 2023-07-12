package com.yapp.bol.terms.dto

import com.yapp.bol.terms.TermsCode

data class TermsResponse(
    val contents: List<TermsItemResponse>
)

data class TermsItemResponse(
    val code: TermsCode,
    val title: String,
    val url: String,
    val isRequired: Boolean,
)

fun TermsCode.toResponse(host: String): TermsItemResponse = TermsItemResponse(
    code = this,
    title = this.title,
    url = "$host/${this.path}",
    isRequired = this.isRequired,
)

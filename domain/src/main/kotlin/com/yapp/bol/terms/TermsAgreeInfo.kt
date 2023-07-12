package com.yapp.bol.terms

data class TermsAgreeInfo(
    val termsCode: TermsCode,
    val isAgree: Boolean,
)

operator fun List<TermsAgreeInfo>.get(termsCode: TermsCode): TermsAgreeInfo? {
    return find { it.termsCode == termsCode }
}

fun List<TermsAgreeInfo>.existAgreed(termsCode: TermsCode): Boolean {
    return get(termsCode)?.isAgree ?: false
}

fun List<TermsAgreeInfo>.existUpdatedTerms(): Boolean {
    return any { it.isAgree && it.termsCode.nextVersion != null }
}

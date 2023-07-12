package com.yapp.bol.terms

enum class TermsCode(
    val title: String,
    val path: String,
    val isRequired: Boolean,
    val displayOrder: Int,
    val nextVersion: TermsCode?,
    val isUse: Boolean = true,
) {
    SERVICE_V1(
        title = "서비스 이용약관",
        path = "terms.html",
        isRequired = true,
        displayOrder = 1,
        nextVersion = null,
    ),
    PRIVACY_V1(
        title = "개인정보 처리방침",
        path = "privacy.html",
        isRequired = true,
        displayOrder = 2,
        nextVersion = null,
    ),
    ;

    companion object {
        fun latestTerms(): List<TermsCode> {
            return TermsCode.values()
                .filter { it.path != null || it.nextVersion != null }
                .sortedBy { it.displayOrder }
        }
    }
}

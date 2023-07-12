package com.yapp.bol.terms

import com.yapp.bol.auth.UserId
import org.springframework.stereotype.Component

@Component
internal class TermsQueryRepositoryImpl(
    private val termsAgreeRepository: TermsAgreeRepository,
) : TermsQueryRepository {

    override fun getSavedTermsByUserId(userId: UserId): List<TermsAgreeInfo> {
        return termsAgreeRepository.findByUserId(userId.value)
            .map { TermsAgreeInfo(it.code, it.isAgree) }
    }
}

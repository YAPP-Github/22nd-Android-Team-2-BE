package com.yapp.bol.terms

import com.yapp.bol.auth.UserId
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
internal class TermsCommandRepositoryImpl(
    private val termsAgreeRepository: TermsAgreeRepository,
) : TermsCommandRepository {

    @Transactional
    override fun saveTermsAgreeInfo(userId: UserId, termsCode: List<TermsAgreeInfo>) {
        val list = termsAgreeRepository.findByUserId(userId.value)

        val entities = termsCode
            .filter { info -> list.any { entity -> info.termsCode == entity.code } }
            .map { TermsAgreeEntity.of(userId.value, it.termsCode, it.isAgree) }

        termsAgreeRepository.saveAll(entities)
    }
}

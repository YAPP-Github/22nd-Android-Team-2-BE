package com.yapp.bol.onboarding

import com.yapp.bol.NotFoundUserException
import com.yapp.bol.auth.UserId
import com.yapp.bol.terms.TermsQueryRepository
import com.yapp.bol.terms.existUpdatedTerms
import com.yapp.bol.user.UserQueryRepository
import org.springframework.stereotype.Service

@Service
class OnboardingServiceImpl(
    private val termsQueryRepository: TermsQueryRepository,
    private val userQueryRepository: UserQueryRepository,
) : OnboardingService {
    override fun getRemainOnboarding(userId: UserId): List<OnboardingType> {
        val result = mutableListOf<OnboardingType>()

        // 약관 관련
        val termsList = termsQueryRepository.getSavedTermsByUserId(userId)
        if (termsList.isEmpty()) {
            result.add(OnboardingType.TERMS)
        } else if (termsList.existUpdatedTerms()) {
            result.add(OnboardingType.UPDATE_TERMS)
        }

        // 닉네임 입력 관련
        val user = userQueryRepository.getUser(userId) ?: throw NotFoundUserException
        if (user.nickname == null) result.add(OnboardingType.NICKNAME)

        return result
    }
}

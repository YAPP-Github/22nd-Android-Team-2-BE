package com.yapp.bol.user.dto

import com.yapp.bol.onboarding.OnboardingType

data class CheckOnboardResponse(
    val onboarding: List<OnboardingType>
)

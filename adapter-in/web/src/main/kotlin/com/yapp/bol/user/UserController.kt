package com.yapp.bol.user

import com.yapp.bol.EmptyResponse
import com.yapp.bol.UnknownException
import com.yapp.bol.auth.getSecurityUserIdOrThrow
import com.yapp.bol.group.GroupService
import com.yapp.bol.group.dto.toResponse
import com.yapp.bol.onboarding.OnboardingService
import com.yapp.bol.user.dto.CheckOnboardResponse
import com.yapp.bol.user.dto.JoinedGroupResponse
import com.yapp.bol.user.dto.MyInfoResponse
import com.yapp.bol.user.dto.PutUserInfoRequest
import com.yapp.bol.user.dto.toResponse
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/v1/user")
@RestController
class UserController(
    private val userService: UserService,
    private val groupService: GroupService,
    private val onboardingService: OnboardingService
) {

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me/onboarding")
    fun checkOnboard(): CheckOnboardResponse {
        val userId = getSecurityUserIdOrThrow()
        val onboardList = onboardingService.getRemainOnboarding(userId)

        return CheckOnboardResponse(onboardList)
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    fun getMe(): MyInfoResponse {
        val userId = getSecurityUserIdOrThrow()
        val user = userService.getUser(userId) ?: throw UnknownException

        return user.toResponse()
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me/group")
    fun getJoinedGroups(): JoinedGroupResponse {
        val userId = getSecurityUserIdOrThrow()

        val groups = groupService.getGroupsByUserId(userId)

        return JoinedGroupResponse(groups.map { it.toResponse() })
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/me")
    fun updateUser(
        @RequestBody request: PutUserInfoRequest,
    ): EmptyResponse {
        val userId = getSecurityUserIdOrThrow()
        val user = User(
            id = userId,
            nickname = request.nickname,
        )

        userService.putUser(user)
        return EmptyResponse
    }
}

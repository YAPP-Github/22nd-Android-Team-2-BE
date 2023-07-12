package com.yapp.bol.terms

import com.yapp.bol.EmptyResponse
import com.yapp.bol.auth.getSecurityUserIdOrThrow
import com.yapp.bol.terms.dto.AgreeTermsRequest
import com.yapp.bol.terms.dto.TermsResponse
import com.yapp.bol.terms.dto.toResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/terms")
class TermsController(
    @Value("\${bol.server.host}")
    private val host: String,
    private val termsService: TermsService,
) {

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun getTerms(): TermsResponse {
        val userId = getSecurityUserIdOrThrow()
        val list = termsService.getNeedTermsAgreeList(userId)
        return TermsResponse(
            list.map { it.toResponse(host) }
        )
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    fun agreeTerms(
        @RequestBody request: AgreeTermsRequest,
    ): EmptyResponse {
        val userId = getSecurityUserIdOrThrow()

        val termsInfo = mutableListOf<TermsAgreeInfo>()

        request.agree?.forEach {
            termsInfo.add(TermsAgreeInfo(it, true))
        }

        request.disagree?.forEach {
            termsInfo.add(TermsAgreeInfo(it, false))
        }

        termsService.agreeTerms(userId, termsInfo)

        return EmptyResponse
    }
}

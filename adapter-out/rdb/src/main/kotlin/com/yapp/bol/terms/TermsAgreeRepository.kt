package com.yapp.bol.terms

import org.springframework.data.jpa.repository.JpaRepository

internal interface TermsAgreeRepository : JpaRepository<TermsAgreeEntity, Long> {

    fun findByUserId(userId: Long): List<TermsAgreeEntity>
    fun findByUserIdAndCode(userId: Long, code: TermsCode): TermsAgreeEntity?
}

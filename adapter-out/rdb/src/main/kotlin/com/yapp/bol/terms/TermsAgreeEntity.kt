package com.yapp.bol.terms

import com.yapp.bol.AuditingEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "agreed_terms")
internal class TermsAgreeEntity : AuditingEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agreed_terms_id")
    var id: Long = 0
        protected set

    @Column(name = "user_id")
    var userId: Long = 0
        protected set

    @Column(name = "code")
    @Enumerated(EnumType.STRING)
    lateinit var code: TermsCode
        protected set

    @Column(name = "is_agree")
    var isAgree: Boolean = true
        protected set

    companion object {
        fun of(
            userId: Long,
            code: TermsCode,
            isAgree: Boolean
        ): TermsAgreeEntity = TermsAgreeEntity().apply {
            this.userId = userId
            this.code = code
            this.isAgree = isAgree
        }
    }
}

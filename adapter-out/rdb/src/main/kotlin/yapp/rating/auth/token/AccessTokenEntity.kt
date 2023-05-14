package yapp.rating.auth.token

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import yapp.rating.AuditingEntity

@Entity
@Table(name = "auth_access_token")
internal class AccessTokenEntity(
    userId: Long,
    accessToken: ByteArray,
    expireAt: LocalDateTime,
) : AuditingEntity() {
    @Id
    @Column(name = "access_token_id")
    var id: Long = 0
        protected set

    @Column(name = "user_id")
    var userId: Long = userId
        protected set

    @Column(name = "access_token")
    var accessToken: ByteArray = accessToken
        protected set

    @Column(name = "expire_at")
    var expireAt: LocalDateTime = expireAt
        protected set
}
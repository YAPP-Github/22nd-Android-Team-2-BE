package com.yapp.bol.auth.token

import com.yapp.bol.auth.Token
import com.yapp.bol.auth.UserId
import com.yapp.bol.toDate
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.security.Key
import java.time.LocalDateTime

internal class JwtUtils(
    private val secretKey: ByteArray,
) : TokenUtils {
    override fun generate(userId: UserId, expiredAt: LocalDateTime): Token {
        val token = Jwts.builder()
            .setId(userId.toString())
            .setExpiration(expiredAt.toDate())
            .signWith(getSigningKey())
            .compact()

        return Token(token, userId, expiredAt)
    }

    override fun validate(token: String): Boolean =
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: JwtException) {
            false
        }

    override fun getUserId(token: String): Long =
        Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token).body.id.toLong()

    private fun getSigningKey(): Key = Keys.hmacShaKeyFor(secretKey)
}

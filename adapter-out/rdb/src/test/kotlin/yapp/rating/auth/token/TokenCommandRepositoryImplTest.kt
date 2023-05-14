package yapp.rating.auth.token

import io.kotest.core.spec.style.FunSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime
import yapp.rating.auth.Token

class TokenCommandRepositoryImplTest : FunSpec() {
    private val accessTokenRepository: AccessTokenRepository = mockk()
    private val refreshTokenRepository: RefreshTokenRepository = mockk()
    private val sut = TokenCommandRepositoryImpl(
        accessTokenRepository,
        refreshTokenRepository,
    )

    init {
        val token = Token(
            userId = 123L,
            value = "123123asdfasdf",
            expireAt = LocalDateTime.now().plusDays(1),
        )

        test("saveAccessToken") {
            // given
            every { accessTokenRepository.save(any()) } returns AccessTokenEntity(
                userId = 123L,
                accessToken = ByteArray(20),
                expireAt = token.expireAt,
            )

            // when
            sut.saveAccessToken(token)

            // then
            verify { accessTokenRepository.save(any()) }
        }

        test("saveRefreshToken") {
            // given
            every { refreshTokenRepository.save(any()) } returns RefreshTokenEntity(
                userId = 123L,
                refreshToken = ByteArray(20),
                expireAt = token.expireAt,
            )

            // when
            sut.saveRefreshToken(token)

            // then
            verify { refreshTokenRepository.save(any()) }
        }
    }
}
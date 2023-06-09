package com.yapp.bol.auth

import com.yapp.bol.SocialLoginFailedException
import com.yapp.bol.auth.social.SocialLoginClient
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDateTime

class AuthServiceImplTest : FunSpec() {
    private val socialLoginClient: SocialLoginClient = mockk()
    private val authCommandRepository: AuthCommandRepository = mockk()
    private val authQueryRepository: AuthQueryRepository = mockk()
    private val tokenService: TokenService = mockk()

    private val sut = AuthServiceImpl(
        socialLoginClient,
        authCommandRepository,
        authQueryRepository,
        tokenService,
    )

    override fun isolationMode() = IsolationMode.InstancePerTest

    init {
        val userId = UserId(123L)
        val accessTokenMock = Token("accessToken", userId, LocalDateTime.now())
        val refreshTokenMock = Token("refreshToken", userId, LocalDateTime.now())

        context("소셜 로그인 성공") {
            // given
            val loginType = LoginType.KAKAO_ACCESS_TOKEN
            val kakaoToken = "KAKAO_TOKEN"
            val kakaoUserId = "KAKAO_USER_ID"

            every { socialLoginClient.login(loginType, kakaoToken) } returns object : SocialUser {
                override val id: String = kakaoUserId
            }

            every { tokenService.generateAccessToken(userId) } returns accessTokenMock
            every { tokenService.generateRefreshToken(userId) } returns refreshTokenMock

            test("기존 회원") {
                // given
                every { authQueryRepository.findAuthUser(loginType, kakaoUserId) } returns AuthUser(userId)

                // when
                val result = sut.login(loginType, kakaoToken)

                // then
                verify(exactly = 0) { authCommandRepository.registerUser(any(), any()) }
                result.accessToken shouldBe accessTokenMock
                result.refreshToken shouldBe refreshTokenMock
            }

            test("신규 회원") {
                // given
                every { authQueryRepository.findAuthUser(loginType, kakaoUserId) } returns null
                every { authCommandRepository.registerUser(loginType, kakaoUserId) } returns AuthUser(userId)

                // when
                val result = sut.login(loginType, kakaoToken)

                // then
                verify(exactly = 1) { authCommandRepository.registerUser(loginType, kakaoUserId) }
                result.accessToken shouldBe accessTokenMock
                result.refreshToken shouldBe refreshTokenMock
            }
        }

        test("소셜 로그인 실패") {
            // given
            val loginType = LoginType.KAKAO_ACCESS_TOKEN
            val kakaoToken = "KAKAO_TOKEN"

            every { socialLoginClient.login(loginType, kakaoToken) } throws SocialLoginFailedException()

            // when & then
            shouldThrow<SocialLoginFailedException> {
                sut.login(loginType, kakaoToken)
            }
            verify(exactly = 0) { authCommandRepository.registerUser(any(), any()) }
            verify(exactly = 0) { authQueryRepository.findAuthUser(any(), any()) }
            verify(exactly = 0) { tokenService.generateAccessToken(any()) }
            verify(exactly = 0) { tokenService.generateRefreshToken(any()) }
        }
    }
}

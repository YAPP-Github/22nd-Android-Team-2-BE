package com.yapp.bol

import java.lang.RuntimeException

sealed class BolRatingException(
    val code: String,
    val status: Int,
    override val message: String,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)

sealed class AuthException(code: String, status: Int, message: String, cause: Throwable? = null) :
    BolRatingException(code, status, message, cause)

class SocialLoginFailedException(cause: Throwable? = null) : AuthException("Auth001", 400, "로그인이 실패했습니다.", cause)
object InvalidTokenException : AuthException("Auth002", 400, "유효하지 않는 토큰 입니다.")
object ExpiredTokenException : AuthException("Auth003", 400, "만료된 토큰 입니다.")
class UnAuthenticationException(cause: Throwable? = null) : AuthException("Auth004", 401, "로그인이 필요합니다.", cause)
class UnAuthorizationException(cause: Throwable? = null) : AuthException("Auth005", 403, "권한이 없습니다.", cause)

object NotFoundUserException : BolRatingException("User001", 400, "유저를 찾을 수 없습니다.")

object IllegalFileStateException : BolRatingException("File001", 500, "요청한 파일의 Status가 올바르지 않습니다.")
object NotFoundFileException : BolRatingException("File002", 400, "파일을 찾을 수 없습니다.")

sealed class GroupException(code: String, message: String, cause: Throwable? = null) :
    BolRatingException(code = code, status = 400, message = message, cause = cause)

object InvalidGroupNameException : GroupException("Group001", "그룹 이름이 잘못되었습니다.")

object InvalidGroupDescriptionException :
    GroupException("Group002", "그룹 설명이 잘못되었습니다.")

object InvalidGroupOrganizationException :
    GroupException("Group003", "그룹 소속이 잘못되었습니다.")

object NotFoundGroupException : GroupException("Group004", "그룹을 찾을 수 없습니다.")
object AccessCodeNotMatchException : GroupException("Group005", "참여 코드가 올바르지 않습니다.")

sealed class MemberException(code: String, status: Int, message: String, cause: Throwable? = null) :
    BolRatingException(code = code, status = status, message = message, cause = cause)

object DuplicatedMemberNicknameException : MemberException("Member002", 400, "중복된 멤버 닉네임입니다.")
object DuplicatedMembersNicknameException : MemberException("Member003", 500, "멤버들 간에 중복된 닉네임이 존재합니다.")
object MultiOwnerException : MemberException("Member004", 500, "그룹장이 2명 이상 존재합니다.")
object InvalidMemberRoleException : MemberException("Member005", 500, "맴버의 상태가 잘 못 되어 었습니다.")
object AlreadyExistMemberException : MemberException("Member006", 400, "이미 가입된 그룹입니다.")

object NotExistRequiredTermsException : BolRatingException("Onboard001", 400, "필수 약관이 동의 되지 않았습니다.")
object OldVersionTermsException : BolRatingException("Onboard002", 400, "구버전 약관을 요청 했습니다.")

object UnknownException : BolRatingException("BOL000", 500, "알 수 없는 에러가 발생했습니다. 다시 시도해주세요.")
object InvalidRequestException : BolRatingException("BOL001", 400, "유효하지 않은 요청입니다.")
object InvalidNicknameException : BolRatingException("BOL002", 400, "닉네임이 잘못되었습니다.")

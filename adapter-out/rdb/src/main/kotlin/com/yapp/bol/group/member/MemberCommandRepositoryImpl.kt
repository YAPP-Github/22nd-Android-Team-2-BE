package com.yapp.bol.group.member

import org.springframework.stereotype.Repository

@Repository
internal class MemberCommandRepositoryImpl(
    private val memberRepository: MemberRepository,
) : MemberCommandRepository {
    override fun createMember(member: Member): Member {
        return memberRepository.save(member.toEntity()).toDomain()
    }
}

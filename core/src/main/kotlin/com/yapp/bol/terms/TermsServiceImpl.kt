package com.yapp.bol.terms

import com.yapp.bol.NotExistRequiredTermsException
import com.yapp.bol.OldVersionTermsException
import com.yapp.bol.auth.UserId
import org.springframework.stereotype.Service

@Service
class TermsServiceImpl(
    private val termsQueryRepository: TermsQueryRepository,
    private val termsCommandRepository: TermsCommandRepository,
) : TermsService {

    private val wholeTerms: List<TermsCode> = TermsCode.latestTerms()

    override fun getNeedTermsAgreeList(userId: UserId): List<TermsCode> {
        val list = termsQueryRepository.getSavedTermsByUserId(userId)

        return wholeTerms
            .filter { baseTerms ->
                list.any { baseTerms == it.termsCode }.not()
            }
            .sortedBy { it.displayOrder }
    }

    override fun agreeTerms(userId: UserId, termsInfoList: List<TermsAgreeInfo>) {
        if (termsInfoList.any { it.termsCode.nextVersion != null }) throw OldVersionTermsException
        if (getNeedTermsAgreeList(userId)
            .filter { it.isRequired }
            .any { termsInfoList.existAgreed(it).not() }
        ) throw NotExistRequiredTermsException

        termsCommandRepository.saveTermsAgreeInfo(userId, termsInfoList)
    }
}

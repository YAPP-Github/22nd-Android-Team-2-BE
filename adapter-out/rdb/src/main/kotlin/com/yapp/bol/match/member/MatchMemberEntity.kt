package com.yapp.bol.match.member

import com.yapp.bol.AuditingEntity
import com.yapp.bol.group.member.MemberId
import com.yapp.bol.match.MatchId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "match_member")
class MatchMemberEntity : AuditingEntity() {
    @Id
    @Column(name = "match_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        protected set

    @Column(name = "match_id")
    var matchId: Long = 0
        protected set

    @Column(name = "member_id")
    var memberId: Long = 0
        protected set

    @Column(name = "score")
    var score: Int = 0
        protected set

    @Column(name = "ranking")
    var ranking: Int = 0
        protected set

    @Column(name = "previous_score")
    var previousScore: Int = 0
        protected set

    companion object {
        fun of(
            id: Long,
            matchId: Long,
            memberId: Long,
            score: Int,
            ranking: Int,
            previousScore: Int
        ) = MatchMemberEntity().apply {
            this.id = id
            this.matchId = matchId
            this.memberId = memberId
            this.score = score
            this.ranking = ranking
            this.previousScore = previousScore
        }
    }
}

internal fun MatchMember.toEntity(matchId: MatchId, memberId: MemberId): MatchMemberEntity = MatchMemberEntity.of(
    id = this.id.value,
    matchId = matchId.value,
    memberId = memberId.value,
    score = this.score,
    ranking = this.ranking,
    previousScore = this.previousScore,
)

internal fun MatchMemberEntity.toDomain(): MatchMember = MatchMember(
    id = MatchMemberId(id),
    matchId = MatchId(matchId),
    memberId = MemberId(memberId),
    score = score,
    ranking = ranking,
    previousScore = previousScore,
)

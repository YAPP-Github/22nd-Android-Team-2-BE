package com.yapp.bol.game

import com.yapp.bol.AuditingEntity
import com.yapp.bol.season.SeasonEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "game_member")
class GameMemberEntity : AuditingEntity() {
    @Id
    @Column(name = "game_member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        protected set

    @Column(name = "game_id")
    var gameId: Long = 0
        protected set

    @Column(name = "member_id")
    var memberId: Long = 0
        protected set

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    lateinit var season: SeasonEntity
        protected set

    @Column(name = "final_score")
    var finalScore: Int = 0
        protected set

    @Column(name = "match_count")
    var matchCount: Int = 0
        protected set

    @Column(name = "winning_percentage")
    var winningPercentage: Double = 0.0
        protected set
}

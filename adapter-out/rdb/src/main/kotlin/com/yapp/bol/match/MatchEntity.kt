package com.yapp.bol.match

import com.yapp.bol.AuditingEntity
import com.yapp.bol.game.GameEntity
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
import java.time.LocalDateTime

@Entity
@Table(name = "match_table")
class MatchEntity : AuditingEntity() {
    @Id
    @Column(name = "match_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        protected set

    @Column(name = "log")
    lateinit var log: String
        protected set

    @Column(name = "match_image_url")
    lateinit var matchImageUrl: String
        protected set

    @Column(name = "matched_date")
    lateinit var matchedDate: LocalDateTime
        protected set

    @Column(name = "member_count")
    var memberCount: Int = 0
        protected set

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    lateinit var season: SeasonEntity
        protected set

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    lateinit var game: GameEntity
        protected set

    @Column(name = "group_id")
    var groupId: Long = 0
        protected set
}

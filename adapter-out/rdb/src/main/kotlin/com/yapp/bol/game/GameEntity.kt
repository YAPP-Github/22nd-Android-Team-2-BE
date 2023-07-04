package com.yapp.bol.game

import com.yapp.bol.AuditingEntity
import com.yapp.bol.file.FileEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "game")
class GameEntity : AuditingEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    var id: Long = 0
        protected set

    @Column(name = "name")
    lateinit var name: String
        protected set

    @Column(name = "min_member")
    var minMember: Int = 2
        protected set

    @Column(name = "max_member")
    var maxMember: Int = 4
        protected set

    @Enumerated(EnumType.STRING)
    @Column(name = "rank_type")
    lateinit var rankType: GameRankType
        protected set

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "img_id")
    lateinit var img: FileEntity
        protected set
}

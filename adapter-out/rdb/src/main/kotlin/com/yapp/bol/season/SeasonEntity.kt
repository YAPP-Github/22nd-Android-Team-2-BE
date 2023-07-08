package com.yapp.bol.season

import com.yapp.bol.AuditingEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "season")
class SeasonEntity : AuditingEntity() {
    @Id
    @Column(name = "season_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        protected set

    @Column(name = "group_id")
    var groupId: Long = 0
        protected set
}

internal fun SeasonEntity.toDomain(): Season = Season(
    id = SeasonId(id),
)

package com.yapp.bol.game

import com.yapp.bol.file.FileNameConverter
import com.yapp.bol.group.GroupId
import org.springframework.stereotype.Component

@Component
class GameClient(
    private val gameRepository: GameRepository,
) : GameQueryRepository {
    override fun getGameListByGroupId(groupId: GroupId): List<Game> {
        return gameRepository.getAll().map { it.toDomain() }
    }

    private fun GameEntity.toDomain(): Game = Game(
        id = GameId(this.id),
        name = this.name,
        minMember = this.minMember,
        maxMember = this.maxMember,
        rankType = this.rankType,
        img = FileNameConverter.convertFileUrl(img.name),
    )
}

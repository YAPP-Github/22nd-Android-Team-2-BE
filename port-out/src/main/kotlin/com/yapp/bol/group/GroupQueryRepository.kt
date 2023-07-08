package com.yapp.bol.group

import com.yapp.bol.auth.UserId
import com.yapp.bol.game.GameId
import com.yapp.bol.pagination.offset.PaginationOffsetResponse

interface GroupQueryRepository {
    fun findById(id: GroupId): Group?

    fun getLeaderBoardList(groupId: GroupId, gameId: GameId): List<LeaderBoardMember>

    fun search(keyword: String?, pageNumber: Int, pageSize: Int): PaginationOffsetResponse<Group>

    fun getGroupsByUserId(userId: UserId): List<Group>
}

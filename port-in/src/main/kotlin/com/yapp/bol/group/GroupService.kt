package com.yapp.bol.group

import com.yapp.bol.auth.UserId
import com.yapp.bol.game.GameId
import com.yapp.bol.group.dto.AddGuestDto
import com.yapp.bol.group.dto.CreateGroupDto
import com.yapp.bol.group.dto.GroupMemberList
import com.yapp.bol.group.dto.GroupWithMemberCount
import com.yapp.bol.group.dto.JoinGroupDto
import com.yapp.bol.group.member.OwnerMember
import com.yapp.bol.pagination.offset.PaginationOffsetResponse

interface GroupService {
    fun createGroup(
        createGroupDto: CreateGroupDto
    ): GroupMemberList

    fun joinGroup(request: JoinGroupDto)

    fun searchGroup(
        keyword: String?,
        pageNumber: Int,
        pageSize: Int
    ): PaginationOffsetResponse<GroupWithMemberCount>

    fun addGuest(request: AddGuestDto)

    fun getLeaderBoard(groupId: GroupId, gameId: GameId): List<LeaderBoardMember>

    fun getGroupsByUserId(userId: UserId): List<Group>

    fun checkAccessToken(groupId: GroupId, accessToken: String): Boolean

    fun getGroupWithMemberCount(groupId: GroupId): GroupWithMemberCount

    fun getOwner(groupId: GroupId): OwnerMember
}

package uz.otamurod.socialmediaktor.dao.user

import uz.otamurod.socialmediaktor.model.SignUpParams
import uz.otamurod.socialmediaktor.model.UserRow

interface UserDao {
    suspend fun insert(params: SignUpParams): UserRow?
    suspend fun findByEmail(email: String): UserRow?
    suspend fun updateFollowsCount(follower: Long, following: Long, isFollowing: Boolean): Boolean
}
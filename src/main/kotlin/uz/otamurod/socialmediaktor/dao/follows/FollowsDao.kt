package uz.otamurod.socialmediaktor.dao.follows

interface FollowsDao {
    suspend fun followUser(follower: Long, following: Long): Boolean

    suspend fun unFollowUser(follower: Long, following: Long): Boolean

    suspend fun getFollowers(userId: Long, pageNumber: Int, pageSize: Int): List<Long>

    suspend fun getFollowing(userId: Long, pageNumber: Int, pageSize: Int): List<Long>

    suspend fun isAlreadyFollowing(follower: Long, following: Long): Boolean
}
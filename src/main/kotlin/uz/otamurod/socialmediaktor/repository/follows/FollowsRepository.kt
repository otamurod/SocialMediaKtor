package uz.otamurod.socialmediaktor.repository.follows

import uz.otamurod.socialmediaktor.model.FollowAndUnFollowResponse
import uz.otamurod.socialmediaktor.util.Response

interface FollowsRepository {
    suspend fun followUser(follower: Long, following: Long): Response<FollowAndUnFollowResponse>
    suspend fun unFollowUser(follower: Long, following: Long): Response<FollowAndUnFollowResponse>

}
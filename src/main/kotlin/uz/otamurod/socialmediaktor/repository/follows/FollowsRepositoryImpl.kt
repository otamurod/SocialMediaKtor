package uz.otamurod.socialmediaktor.repository.follows

import io.ktor.http.*
import uz.otamurod.socialmediaktor.dao.follows.FollowsDao
import uz.otamurod.socialmediaktor.dao.user.UserDao
import uz.otamurod.socialmediaktor.model.FollowAndUnFollowResponse
import uz.otamurod.socialmediaktor.util.Response

class FollowsRepositoryImpl(
    private val userDao: UserDao,
    private val followsDao: FollowsDao
) : FollowsRepository {
    override suspend fun followUser(follower: Long, following: Long): Response<FollowAndUnFollowResponse> {
        return if (followsDao.isAlreadyFollowing(follower, following)) {
            Response.Error(
                code = HttpStatusCode.Forbidden,
                data = FollowAndUnFollowResponse(
                    success = false,
                    message = "You are already following this user"
                )
            )
        } else {
            val success = followsDao.followUser(follower, following)
            if (success) {
                userDao.updateFollowsCount(follower, following, isFollowing = true)

                Response.Success(data = FollowAndUnFollowResponse(success = true))
            } else {
                Response.Error(
                    code = HttpStatusCode.InternalServerError,
                    data = FollowAndUnFollowResponse(
                        success = false,
                        message = "Oops, something went wrong on our side, please try again!"
                    )
                )
            }
        }
    }

    override suspend fun unFollowUser(follower: Long, following: Long): Response<FollowAndUnFollowResponse> {
        val success = followsDao.unFollowUser(follower, following)

        return if (success) {
            userDao.updateFollowsCount(follower, following, isFollowing = false)

            Response.Success(data = FollowAndUnFollowResponse(success = true))
        } else {
            Response.Error(
                code = HttpStatusCode.InternalServerError,
                data = FollowAndUnFollowResponse(
                    success = false,
                    message = "Oops, something went wrong on our side, please try again!"
                )
            )
        }
    }
}
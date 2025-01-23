package uz.otamurod.socialmediaktor.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import uz.otamurod.socialmediaktor.model.FollowAndUnFollowResponse
import uz.otamurod.socialmediaktor.model.FollowsParams
import uz.otamurod.socialmediaktor.repository.follows.FollowsRepository

fun Routing.followsRouting() {
    val repository by inject<FollowsRepository>()

    authenticate {
        route(path = "/follow") {
            post {
                val params = call.receiveNullable<FollowsParams>()

                if (params == null) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message = FollowAndUnFollowResponse(
                            success = false,
                            message = "Oops, something went wrong, try again!"
                        )
                    )
                    return@post
                }

                val result = if (params.isFollowing) {
                    repository.followUser(params.follower, params.following)
                } else {
                    repository.unFollowUser(params.follower, params.following)
                }

                call.respond(
                    status = result.code,
                    message = result.data
                )
            }
        }
    }
}
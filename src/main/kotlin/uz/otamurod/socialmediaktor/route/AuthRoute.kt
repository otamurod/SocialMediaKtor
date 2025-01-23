package uz.otamurod.socialmediaktor.route

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import uz.otamurod.socialmediaktor.model.AuthResponse
import uz.otamurod.socialmediaktor.model.SignInParams
import uz.otamurod.socialmediaktor.model.SignUpParams
import uz.otamurod.socialmediaktor.repository.auth.AuthRepository

fun Routing.authRouting() {
    val repository by inject<AuthRepository>()

    route(path = "/signup") {
        post {
            val params = call.receiveNullable<SignUpParams>()

            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Invalid credentials!"
                    )
                )

                return@post
            }

            val result = repository.signUp(params)
            call.respond(
                status = result.code,
                message = result.data
            )
        }
    }

    route(path = "/login") {
        post {
            val params = call.receiveNullable<SignInParams>()

            if (params == null) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = AuthResponse(
                        errorMessage = "Invalid credentials!"
                    )
                )

                return@post
            }

            val result = repository.signIn(params)
            call.respond(
                status = result.code,
                message = result.data
            )
        }
    }
}
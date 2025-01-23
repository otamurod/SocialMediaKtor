package uz.otamurod.socialmediaktor.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import uz.otamurod.socialmediaktor.route.authRouting
import uz.otamurod.socialmediaktor.route.followsRouting

fun Application.configureRouting() {
    routing {
        authRouting()
        followsRouting()
    }
}

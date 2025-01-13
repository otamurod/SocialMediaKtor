package uz.otamurod.socialmediaktor.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import uz.otamurod.socialmediaktor.route.authRouting

fun Application.configureRouting() {
    routing {
        authRouting()
    }
}

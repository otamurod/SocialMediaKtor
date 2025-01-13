package uz.otamurod.socialmediaktor

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import uz.otamurod.socialmediaktor.dao.DatabaseFactory
import uz.otamurod.socialmediaktor.di.configureDI
import uz.otamurod.socialmediaktor.plugins.configureRouting
import uz.otamurod.socialmediaktor.plugins.configureSecurity
import uz.otamurod.socialmediaktor.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDI()
    DatabaseFactory.init()
    configureSerialization()
    configureSecurity()
    configureRouting()
}

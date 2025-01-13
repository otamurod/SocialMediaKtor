package uz.otamurod.socialmediaktor.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import uz.otamurod.socialmediaktor.model.AuthResponse

// Please read the jwt property from the config file if you are using EngineMain
private val jwtAudience = System.getenv("jwt-audience")
private val jwtIssuer = System.getenv("jwt-domain")
private val jwtSecret = System.getenv("secret")

private const val CLAIM = "email"

fun Application.configureSecurity() {
    authentication {
        jwt {
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim(CLAIM) != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { defaultScheme, realm ->
                call.respond(
                    status = HttpStatusCode.Unauthorized,
                    message = AuthResponse(
                        errorMessage = "The token is not valid or has expired!"
                    )
                )
            }
        }
    }
}

fun generateToken(email: String): String {
    return JWT.create()
        .withAudience(jwtAudience)
        .withIssuer(jwtIssuer)
        .withClaim(CLAIM, email)
        .sign(Algorithm.HMAC256(jwtSecret))
}
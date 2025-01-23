package uz.otamurod.socialmediaktor.repository.auth

import io.ktor.http.*
import uz.otamurod.socialmediaktor.dao.user.UserDao
import uz.otamurod.socialmediaktor.model.AuthResponse
import uz.otamurod.socialmediaktor.model.AuthResponseData
import uz.otamurod.socialmediaktor.model.SignInParams
import uz.otamurod.socialmediaktor.model.SignUpParams
import uz.otamurod.socialmediaktor.plugins.generateToken
import uz.otamurod.socialmediaktor.security.hashPassword
import uz.otamurod.socialmediaktor.util.Response

class AuthRepositoryImpl(
    private val userDao: UserDao
) : AuthRepository {
    override suspend fun signUp(params: SignUpParams): Response<AuthResponse> {
        return if (isUserAlreadyExist(params.email)) {
            Response.Error(
                code = HttpStatusCode.Conflict,
                data = AuthResponse(
                    errorMessage = "A user with this email is already exist!"
                )
            )
        } else {
            val newUser = userDao.insert(params)

            if (newUser == null) {
                Response.Error(
                    code = HttpStatusCode.InternalServerError,
                    data = AuthResponse(
                        errorMessage = "Oops, Sorry, we could not register a user, try later!"
                    )
                )
            } else {
                Response.Success(
                    data = AuthResponse(
                        data = AuthResponseData(
                            id = newUser.id,
                            username = newUser.username,
                            bio = newUser.bio,
                            avatar = newUser.imageUrl,
                            token = generateToken(params.email),
                        )
                    )
                )
            }
        }
    }

    override suspend fun signIn(params: SignInParams): Response<AuthResponse> {
        val user = userDao.findByEmail(params.email)

        return if (user == null) {
            Response.Error(
                code = HttpStatusCode.NotFound,
                data = AuthResponse(
                    errorMessage = "Invalid credentials, no user found with this email!"
                )
            )
        } else {
            if (user.password == hashPassword(params.password)) {
                Response.Success(
                    data = AuthResponse(
                        data = AuthResponseData(
                            id = user.id,
                            username = user.username,
                            bio = user.bio,
                            token = generateToken(params.email),
                            followingCount = user.followingCount,
                            followersCount = user.followersCount
                        )
                    )
                )
            } else {
                Response.Error(
                    code = HttpStatusCode.Forbidden,
                    data = AuthResponse(
                        errorMessage = "Invalid credentials, wrong password!"
                    )
                )
            }
        }
    }

    private suspend fun isUserAlreadyExist(email: String): Boolean {
        return userDao.findByEmail(email) != null
    }
}
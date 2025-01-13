package uz.otamurod.socialmediaktor.dao.user

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import uz.otamurod.socialmediaktor.dao.DatabaseFactory.dbQuery
import uz.otamurod.socialmediaktor.model.SignUpParams
import uz.otamurod.socialmediaktor.model.User
import uz.otamurod.socialmediaktor.model.UserRow
import uz.otamurod.socialmediaktor.security.hashPassword

class UserDaoImpl : UserDao {
    override suspend fun insert(params: SignUpParams): User? {
        return dbQuery {
            val insertStatement = UserRow.insert {
                it[username] = params.username
                it[email] = params.email
                it[password] = hashPassword(params.password)
            }

            insertStatement.resultedValues?.singleOrNull()?.let {
                rowToUser(it)
            }
        }
    }

    override suspend fun findByEmail(email: String): User? {
        return dbQuery {
            UserRow.selectAll().where { UserRow.email eq email }
                .map { rowToUser(it) }
                .singleOrNull()
        }
    }

    private fun rowToUser(row: ResultRow): User {
        return User(
            id = row[UserRow.id],
            username = row[UserRow.username],
            bio = row[UserRow.bio],
            password = row[UserRow.password],
            avatar = row[UserRow.avatar],
        )
    }
}
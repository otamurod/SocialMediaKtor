package uz.otamurod.socialmediaktor.dao.user

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.plus
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import uz.otamurod.socialmediaktor.dao.DatabaseFactory.dbQuery
import uz.otamurod.socialmediaktor.model.SignUpParams
import uz.otamurod.socialmediaktor.model.UserRow
import uz.otamurod.socialmediaktor.security.hashPassword
import uz.otamurod.socialmediaktor.util.IdGenerator

class UserDaoImpl : UserDao {
    override suspend fun insert(params: SignUpParams): UserRow? {
        return dbQuery {
            val insertStatement = UserTable.insert {
                it[id] = IdGenerator.generateId()
                it[username] = params.username
                it[email] = params.email
                it[password] = hashPassword(params.password)
            }

            insertStatement.resultedValues?.singleOrNull()?.let {
                rowToUser(it)
            }
        }
    }

    override suspend fun findByEmail(email: String): UserRow? {
        return dbQuery {
            UserTable.selectAll().where { UserTable.email eq email }
                .map { rowToUser(it) }
                .singleOrNull()
        }
    }

    override suspend fun updateFollowsCount(follower: Long, following: Long, isFollowing: Boolean): Boolean {
        return dbQuery {
            val count = if (isFollowing) +1 else -1

            val success1 = UserTable.update({ UserTable.id eq follower }) {
                it.update(
                    column = followingCount,
                    value = followingCount.plus(count)
                )
            } > 0

            val success2 = UserTable.update({ UserTable.id eq following }) {
                it.update(
                    column = followersCount,
                    value = followersCount.plus(count)
                )
            } > 0

            success1 and success2
        }
    }

    private fun rowToUser(row: ResultRow): UserRow {
        return UserRow(
            id = row[UserTable.id],
            username = row[UserTable.username],
            bio = row[UserTable.bio],
            password = row[UserTable.password],
            imageUrl = row[UserTable.imageUrl],
            followersCount = row[UserTable.followersCount],
            followingCount = row[UserTable.followingCount],
        )
    }
}
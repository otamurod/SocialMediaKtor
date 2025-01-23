package uz.otamurod.socialmediaktor.dao.follows

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import uz.otamurod.socialmediaktor.dao.DatabaseFactory.dbQuery
import uz.otamurod.socialmediaktor.dao.follows.FollowsTable.followerId
import uz.otamurod.socialmediaktor.dao.follows.FollowsTable.followingId

class FollowsDaoImpl : FollowsDao {
    override suspend fun followUser(follower: Long, following: Long): Boolean {
        return dbQuery {
            val insertStatement = FollowsTable.insert {
                it[followerId] = follower
                it[followingId] = following
            }

            insertStatement.resultedValues?.singleOrNull() != null
        }
    }

    override suspend fun unFollowUser(follower: Long, following: Long): Boolean {
        return dbQuery {
            FollowsTable.deleteWhere {
                (followerId eq follower) and (followingId eq following)
            } > 0
        }
    }

    override suspend fun getFollowers(userId: Long, pageNumber: Int, pageSize: Int): List<Long> {
        return dbQuery {
            FollowsTable.selectAll().where { followerId eq userId }
                .orderBy(FollowsTable.followDate, SortOrder.DESC)
                .limit(count = pageSize).offset(start = (pageNumber - 1) * pageSize.toLong())
                .map { it[followingId] }
        }
    }

    override suspend fun getFollowing(userId: Long, pageNumber: Int, pageSize: Int): List<Long> {
        return dbQuery {
            FollowsTable.selectAll().where { followingId eq userId }
                .orderBy(FollowsTable.followDate, SortOrder.DESC)
                .limit(count = pageSize).offset(start = (pageNumber - 1) * pageSize.toLong())
                .map { it[followerId] }
        }
    }

    override suspend fun isAlreadyFollowing(follower: Long, following: Long): Boolean {
        return dbQuery {
            val queryResult = FollowsTable.selectAll().where { (followerId eq follower) and (followingId eq following) }

            queryResult.toList().isNotEmpty()
        }
    }
}
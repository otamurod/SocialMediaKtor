package uz.otamurod.socialmediaktor.security

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private val ALGORITHM = System.getenv("hash-algorithm")
private val HASH_KEY = System.getenv("hash-secret").toByteArray()
private val hMacKey = SecretKeySpec(HASH_KEY, ALGORITHM)

fun hashPassword(password: String): String {
    val hMack = Mac.getInstance(ALGORITHM)
    hMack.init(hMacKey)

    return hex(hMack.doFinal(password.toByteArray()))
}
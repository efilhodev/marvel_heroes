package br.com.efilhodev.marvel_heroes.plugin.retrofit

import okhttp3.internal.and
import java.security.MessageDigest

class Authentication {

    companion object {
        // TODO Deixar essas chaves remotas.
        private const val PRIVATE_KEY = "9fcea95cc9f59ec72022100546207e431df8bbd2"
        const val PUBLIC_KEY = "0f3567d293406aa50df5eeebb0b528e6"

        val timeStamp: String = System.currentTimeMillis().toString()

        fun getHashKey(): String {
            val input = timeStamp + PRIVATE_KEY + PUBLIC_KEY

            val digest = MessageDigest.getInstance("MD5")
            val md5Bytes: ByteArray = digest.digest(input.toByteArray())

            val hash = StringBuilder()
            for (i in md5Bytes.indices) {
                hash.append(Integer.toHexString(md5Bytes[i] and 0xFF or 0x100).substring(1, 3))
            }
            return hash.toString()
        }
    }
}
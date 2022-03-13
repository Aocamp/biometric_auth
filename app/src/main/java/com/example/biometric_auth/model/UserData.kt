package com.example.biometric_auth.model

data class UserData(
    val username: String,
    val alias: String,
    val pass: ByteArray,
    val iv: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserData) return false

        if (username != other.username) return false
        if (alias != other.alias) return false
        if (!pass.contentEquals(other.pass)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + alias.hashCode()
        result = 31 * result + pass.contentHashCode()
        return result
    }
}

package com.horizons.profession.util



data class MainResponse<T>(
    val Status: String?,
   val Error: String?,
    val Data: T?
)


enum class ServerStatusCodes(val code: Int) {
    SUCCESS(200),
    FAIL(401), //fail with message
    SOCIAL_REGISTER(402), //login with social and needs to complete data
    TOKEN_EXPIRED(403),
    ACTIVE_ACCOUNT(405),
}

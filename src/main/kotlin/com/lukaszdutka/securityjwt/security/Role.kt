package com.lukaszdutka.securityjwt.security

import org.springframework.security.core.GrantedAuthority

data class Role(val role: String) : GrantedAuthority {
    companion object {
        const val USER = "USER"
        const val ADMIN = "ADMIN"

        fun user(): Role = Role(USER)
        fun admin(): Role = Role(ADMIN)
    }

    override fun getAuthority(): String = role
}

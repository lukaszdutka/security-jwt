package com.lukaszdutka.securityjwt.security

import org.springframework.security.core.GrantedAuthority

data class Role(val role: String) : GrantedAuthority {
    companion object {
        const val ROLE_USER = "ROLE_USER"
        const val ROLE_ADMIN = "ROLE_ADMIN"

        fun userRole() = Role(ROLE_USER)
        fun adminRole() = Role(ROLE_ADMIN)
    }

    override fun getAuthority(): String = role
}

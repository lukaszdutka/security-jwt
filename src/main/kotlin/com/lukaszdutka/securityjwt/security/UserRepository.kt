package com.lukaszdutka.securityjwt.security

import org.springframework.security.core.userdetails.UserDetailsService

interface UserRepository : UserDetailsService {
    fun saveUser(user: User)
}
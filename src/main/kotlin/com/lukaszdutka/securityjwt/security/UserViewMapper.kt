package com.lukaszdutka.securityjwt.security

import org.springframework.stereotype.Component

@Component
class UserViewMapper {
    fun toUserView(user: User): UserView = UserView("bodyXD")
}

data class UserView(val string: String)
package com.lukaszdutka.securityjwt.security

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("hello")
class HelloRestController {

    @GetMapping("anonymous")
    fun helloAnonymous(): String = "Hello Anonymous"

    @GetMapping("user")
    @RolesAllowed(Role.ROLE_USER)
    fun helloUser(): String = "Hello User"

    @GetMapping("admin")
    @RolesAllowed(Role.ROLE_ADMIN)
    fun helloAdmin(): String = "Hello Admin"
}
package com.lukaszdutka.securityjwt.security

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("hello")
class HelloRestController {

    @GetMapping("user")
    @RolesAllowed(Role.USER)
    fun helloUser(): String = "Hello User"

    @GetMapping("admin")
    @RolesAllowed(Role.ADMIN)
    fun helloAdmin(): String = "Hello Admin"
}
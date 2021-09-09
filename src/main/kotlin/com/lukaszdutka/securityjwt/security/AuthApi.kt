package com.lukaszdutka.securityjwt.security

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/public")
class AuthApi(
    val authenticationManager: AuthenticationManager,
    val jwtTokenUtil: JwtTokenUtil,
    val userViewMapper: UserViewMapper
) {

    @PostMapping("login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<LoginResponseBody> = try {
        val auth: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.username, request.password)
        )

        val user: User = auth.principal as User

        ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_TYPE, "application/json")
            .body(LoginResponseBody(jwtTokenUtil.generateAccessToken(user)))
    } catch (ex: BadCredentialsException) {
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
    }

}

data class LoginResponseBody(val token: String)
data class AuthRequest(val username: String, val password: String)

package com.lukaszdutka.securityjwt.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class SimpleAuthenticationEntryPoint : AuthenticationEntryPoint {
    override fun commence(req: HttpServletRequest?, res: HttpServletResponse?, ex: AuthenticationException?) {
        res?.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex?.message)
    }
}
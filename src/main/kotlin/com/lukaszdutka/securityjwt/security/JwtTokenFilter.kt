package com.lukaszdutka.securityjwt.security

import org.springframework.http.HttpHeaders
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(
    val jwtTokenUtil: JwtTokenUtil,
    val userRepository: UserRepository,
    val roleHierarchy: RoleHierarchyImpl
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (header.isNullOrEmpty() || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response)
            return
        }

        val token = header.split(" ")[1].trim()
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response)
            return
        }

        enhanceSecurityContextWithUserDetails(token, request)
        chain.doFilter(request, response)
    }

    private fun enhanceSecurityContextWithUserDetails(token: String, request: HttpServletRequest) {
        val user = userRepository.loadUserByUsername(jwtTokenUtil.getUsername(token))
        val auth = UsernamePasswordAuthenticationToken(
            user, null, roleHierarchy.getReachableGrantedAuthorities(user.authorities)
        )

        auth.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = auth
    }
}
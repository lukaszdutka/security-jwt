package com.lukaszdutka.securityjwt.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.core.GrantedAuthorityDefaults
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.FilterInvocation
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@EnableWebSecurity
@EnableGlobalMethodSecurity(
    jsr250Enabled = true,
    prePostEnabled = true
)
class SecurityConfig(val userRepository: UserRepository, val jwtTokenFilter: JwtTokenFilter) :
    WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userRepository)
    }

    override fun configure(http: HttpSecurity) {
        http.cors()
            .and()
            .csrf().disable()

            .sessionManagement()
            .sessionCreationPolicy(STATELESS)
            .and()

            .exceptionHandling()
            .authenticationEntryPoint(SimpleAuthenticationEntryPoint())
            .and()
            .authorizeRequests()
            .anyRequest().permitAll()
            .and()
            .authorizeRequests()
            .expressionHandler(webExpressionHandler())
            .and()
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter().javaClass)
    }

    @Bean
    fun corsFilter(): CorsFilter = CorsFilter(
        UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", CorsConfiguration().apply {
                allowCredentials = true
                addAllowedOrigin("*")
                addAllowedHeader("*")
                addAllowedMethod("*")
            })
        })

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun roleHierarchy(): RoleHierarchyImpl = RoleHierarchyImpl().apply { setHierarchy("ADMIN > USER") }

    private fun webExpressionHandler(): SecurityExpressionHandler<FilterInvocation> =
        DefaultWebSecurityExpressionHandler().apply { setRoleHierarchy(roleHierarchy()) }
}

@Configuration
class GrantedAuthConfiguration {
    @Bean
    fun grantedAuthorityDefaults(): GrantedAuthorityDefaults = GrantedAuthorityDefaults("") // Remove the ROLE_ prefix
}
package com.lukaszdutka.securityjwt.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class User(
    private val id: String,
    private val username: String,
    val email: String,
    private val password: String,
    private val isEnabled: Boolean, //Disabled account can not log in
//    private val isCredentialsNonExpired: Boolean, //credential can be expired,eg. Change the password every three months
//    private val isAccountNonExpired: Boolean, //eg. Demo account（guest） can only be online  24 hours
    private val isAccountNonLocked: Boolean, //eg. Users who malicious attack system,lock their account for one year
    val role: Role, // USER, ADMIN
) : UserDetails {
    private val authorities: Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority(role.authority))

    fun getId(): String = id
    override fun getUsername(): String = username
    override fun getPassword(): String = password
    override fun isEnabled(): Boolean = isEnabled
    override fun isCredentialsNonExpired(): Boolean = true //isCredentialsNonExpired
    override fun isAccountNonExpired(): Boolean = true //isAccountNonExpired
    override fun isAccountNonLocked(): Boolean = isAccountNonLocked
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
}
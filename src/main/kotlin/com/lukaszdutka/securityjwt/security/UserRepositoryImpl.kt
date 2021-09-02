package com.lukaszdutka.securityjwt.security

import com.lukaszdutka.securityjwt.security.Role.Companion.admin
import com.lukaszdutka.securityjwt.security.Role.Companion.user
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl : UserRepository {
    private val map = HashMap<String, User>().apply {
        this["admin"] = User(
            "1",
            "admin",
            "admin@gmail.com",
            "\$argon2id\$v=19\$m=15360,t=2,p=1\$faSKDbx74S76/T9CPFAcuA\$4rNl9GZPrAdKTSSgdE4H1oCph0qyaB3T5VHSz9kdi+Y", //"password123!",
            true, true, admin()
        )
        this["user"] = User(
            "2",
            "user",
            "user@gmail.com",
            "\$argon2id\$v=19\$m=15360,t=2,p=1\$faSKDbx74S76/T9CPFAcuA\$4rNl9GZPrAdKTSSgdE4H1oCph0qyaB3T5VHSz9kdi+Y", //"password123!",
            true, true, user()
        )
    }

    override fun saveUser(user: User) {
        map[user.username] = user
    }

    override fun loadUserByUsername(username: String): User =
        map[username] ?: throw UsernameNotFoundException("User with username='$username' not found.")
}
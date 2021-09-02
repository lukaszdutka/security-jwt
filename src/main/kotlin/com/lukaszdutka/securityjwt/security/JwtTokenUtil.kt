package com.lukaszdutka.securityjwt.security

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys.hmacShaKeyFor
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.*

@Component
class JwtTokenUtil {
    private val jwtSecret =
        "zdtlD3JK56m6wTTgsNFhqzjqPzdtlD3JK56m6wTTgsNFhqzjqPzdtlD3JK56m6wTTgsNFhqzjqPzdtlD3JK56m6wTTgsNFhqzjqP"
    private val jwtSecretKey = hmacShaKeyFor(jwtSecret.asBase64())
    private val jwtIssuer = "lukaszdutka.io"
    private val oneWeekInMillis = 7 * 24 * 60 * 60 * 1000

    fun generateAccessToken(user: User): String = Jwts.builder()
        .setSubject("${user.getId()},${user.username}")
        .setIssuer(jwtIssuer)
        .setIssuedAt(Date())
        .setExpiration(Date(System.currentTimeMillis() + oneWeekInMillis))
        .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
        .compact()

    fun getUsername(token: String): String = Jwts.parserBuilder()
        .setSigningKey(jwtSecretKey)
        .build()
        .parseClaimsJws(token)
        .body.subject
        .split(",")[1]

    fun validate(token: String): Boolean {
        try {
            Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
            return true
        } catch (ex: SignatureException) {
            println("Invalid JWT signature - ${ex.message}")
        } catch (ex: MalformedJwtException) {
            println("Invalid JWT token - ${ex.message}")
        } catch (ex: ExpiredJwtException) {
            println("Expired JWT token - ${ex.message}")
        } catch (ex: UnsupportedJwtException) {
            println("Unsupported JWT token - ${ex.message}")
        } catch (ex: IllegalArgumentException) {
            println("JWT claims string is empty - ${ex.message}")
        }
        return false
    }
}

private fun String.asBase64(): ByteArray? = Decoders.BASE64.decode(this)

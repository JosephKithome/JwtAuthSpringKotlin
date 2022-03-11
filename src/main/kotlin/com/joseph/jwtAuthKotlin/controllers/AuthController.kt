package com.joseph.jwtAuthKotlin.controllers

import com.joseph.jwtAuthKotlin.Utils.Message
import com.joseph.jwtAuthKotlin.dtos.LoginDTO
import com.joseph.jwtAuthKotlin.models.User
import com.joseph.jwtAuthKotlin.dtos.UserDTO
import com.joseph.jwtAuthKotlin.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("api/")
class AuthController(private val userService: UserService) {

    @PostMapping("register")
     fun registerUser(@RequestBody userDto: UserDTO): ResponseEntity<User>{
         val user = User()
        user.username = userDto.username
        user.email = userDto.email
        user.password = userDto.password

        return ResponseEntity.ok(this.userService.saveUser(user))
     }

    @PostMapping("login")
    fun login(@RequestBody body: LoginDTO,response: HttpServletResponse): ResponseEntity<Any> {
        val user = userService.findByEmail(body.email) ?: return ResponseEntity.badRequest().body(Message("User Not Found!"))
        if (!user.comparePassword(body.password )){
            return ResponseEntity.badRequest().body(Message("Incorrect password!"))
        }
        val issuer = user.id.toString()
        val jwt = Jwts.builder()
            jwt.setIssuer(issuer)
            jwt.setExpiration(Date(System.currentTimeMillis() + 60*24*1000))
//            jwt.signWith(SignatureAlgorithm.HS256,"secret")

        val cookie = Cookie("jwt", jwt.toString())
        cookie.isHttpOnly = true
        response.addCookie(cookie)
        return  ResponseEntity.ok(Message("Success"))
    }

    //Get authorized user
    @GetMapping("user")
    fun user(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {

        try {
            if (jwt == null) {
                return ResponseEntity.status(401).body(Message("Unauthorized"))
            }

            val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body

            return ResponseEntity.ok(this.userService.getById(body.issuer.toInt()))
        } catch (e: Exception) {
            return ResponseEntity.status(401).body(Message("Unauthorized"))
        }
    }
    }

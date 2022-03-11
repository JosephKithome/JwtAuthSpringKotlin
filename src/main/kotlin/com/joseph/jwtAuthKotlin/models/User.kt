package com.joseph.jwtAuthKotlin.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Int =0
    @Column
    var username =""
    @Column(unique = true)
    var email = ""
    @Column
    var password = ""
    get() = field
    set(value) {
        val bCryptPasswordEncodert = BCryptPasswordEncoder()
        field = bCryptPasswordEncodert.encode(value)
    }

    //check password matching
    fun comparePassword(password: String): Boolean{
        return BCryptPasswordEncoder().matches(password, this.password)
    }

}
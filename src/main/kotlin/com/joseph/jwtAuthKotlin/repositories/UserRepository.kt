package com.joseph.jwtAuthKotlin.repositories

import com.joseph.jwtAuthKotlin.models.User
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface UserRepository: JpaRepository<User, Id> {

    //find user by id
    fun getById(id: Int): User
    //find user by email
    fun findByEmail(email: String?): User
}
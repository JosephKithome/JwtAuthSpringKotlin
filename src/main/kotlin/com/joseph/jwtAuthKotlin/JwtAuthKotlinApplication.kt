package com.joseph.jwtAuthKotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication (exclude = [SecurityAutoConfiguration::class])
class JwtAuthKotlinApplication

fun main(args: Array<String>) {
	runApplication<JwtAuthKotlinApplication>(*args)
}

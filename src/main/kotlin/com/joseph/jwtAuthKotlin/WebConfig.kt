package com.joseph.jwtAuthKotlin

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig: WebMvcConfigurer {
    override fun addCorsMappings(registry: CorsRegistry) {
//        super.addCorsMappings(registry)
            registry.addMapping("/**")
                .allowedOrigins("http://localhost:8000","http://localhost:8080","http://localhost:4200")
                .allowCredentials(true)

    }
}
package com.example.hellokopring.config

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import org.springframework.context.annotation.Configuration

@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
@Configuration
class SpringDocsConfig {

    // @Bean
    // fun customJWTBearerSecuritySchemaOpenAPI(): OpenAPI {
    //     return OpenAPI()
    //         .components(
    //             Components()
    //                 .addSecuritySchemes(
    //                     "Authorization",
    //                     SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
    //                 )
    //         )
    // }
}

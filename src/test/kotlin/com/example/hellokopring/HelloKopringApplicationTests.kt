package com.example.hellokopring

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles(profiles = ["test"])
class HelloKopringApplicationTests {
    // jira commit prefix msg hook check
    @Test
    fun contextLoads() {
    }
}

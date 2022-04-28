import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"

    val kotlinVersion = "1.6.21"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    kotlin("plugin.jpa") version kotlinVersion
    kotlin("plugin.allopen") version kotlinVersion
    kotlin("plugin.noarg") version kotlinVersion

    // Gradle Use Latest Versions Plugin
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
    id("com.github.ben-manes.versions") version "0.42.0"

    // Kotlin Lint Plugin
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("org.jlleitschuh.gradle.ktlint-idea") version "10.2.1"

    // Flyway
    id("org.flywaydb.flyway") version "8.5.8"

    // SpringDoc (RESTAPI Document)
    id("org.springdoc.openapi-gradle-plugin") version "1.3.4"

    // QueryDSL
    kotlin("kapt") version "1.3.61" // 추가
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
val querydslVersion = "5.0.0"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

extra["springBootAdminVersion"] = "2.6.5"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("de.codecentric:spring-boot-admin-starter-server")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // SpringCloud AWS
    implementation("org.springframework.cloud:spring-cloud-aws-core")
    implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.6.RELEASE") //
    // implementation("io.awspring.cloud:spring-cloud-aws-secrets-manager-config:2.4.1")
    implementation("org.springframework.cloud:spring-cloud-starter-bootstrap:3.1.1")
    implementation("org.springframework.cloud:spring-cloud-starter-aws-secrets-manager-config:2.2.6.RELEASE")

    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

    implementation("io.netty:netty-resolver-dns-native-macos:4.1.76.Final:osx-aarch_64")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // QueryDSL
    implementation("com.querydsl:querydsl-jpa:$querydslVersion")
    implementation("com.google.guava:guava:18.0")
    kapt("com.querydsl:querydsl-apt:$querydslVersion:jpa")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    // mapstruct
    implementation("org.mapstruct:mapstruct:1.4.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.4.2.Final")

    // Hibernate5Module이 지연로딩 되는 객체의 프로퍼티 직렬화를 가능하게 해준다. (jackson ObjectMapper support)
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5:2.13.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")

    implementation("cloud.localstack:localstack-utils:0.2.20")

    // SpringDocs
    implementation("org.springdoc:springdoc-openapi-ui:1.6.8")
    implementation("org.springdoc:springdoc-openapi-kotlin:1.6.7")
    implementation("org.springdoc:springdoc-openapi-security:1.6.7")

    // Redis
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

dependencyManagement {
    imports {
        mavenBom("de.codecentric:spring-boot-admin-dependencies:${property("springBootAdminVersion")}")
    }
}

allOpen { // 추가적으로 열어줄 allOpen
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
noArg {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

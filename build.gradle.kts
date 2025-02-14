val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

val exposed_version: String by project
val h2_version: String by project
val koin_version: String by project
val hikaricp_version: String by project
val ehcache_version: String by project

val lettuce_version: String by project

val kotlinx_coroutines_version: String by project

plugins {
    kotlin("jvm") version "2.0.21"
    id("io.ktor.plugin") version "3.0.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.21"
}

group = "cc.loac"
version = "0.0.1"

application {
    mainClass.set("cc.loac.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers") }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-sessions-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-jackson:$ktor_version")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.16.1")

    implementation("io.ktor:ktor-server-thymeleaf:$ktor_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    // MariaDB 驱动
    implementation("org.mariadb.jdbc:mariadb-java-client:3.3.2")
    // https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
    implementation("com.mysql:mysql-connector-j:8.3.0")

    // H2 驱动
    implementation("com.h2database:h2:$h2_version")

    // 数据库连接池和缓存
    implementation("com.zaxxer:HikariCP:$hikaricp_version")
//    implementation("org.ehcache:ehcache:$ehcache_version")

    // Redis Lettuce
    // https://mvnrepository.com/artifact/io.lettuce/lettuce-core
    implementation("io.lettuce:lettuce-core:$lettuce_version")


    // 根据异常返回指定的页面
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")

    // Ktor 依赖注入
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    // 腾讯云对象存储
    implementation("com.qcloud:cos_api:5.6.205")

    // https://mvnrepository.com/artifact/com.belerweb/pinyin4j
    implementation("com.belerweb:pinyin4j:2.5.1")



    implementation("io.ktor:ktor-server-netty-jvm")
//    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // https://mvnrepository.com/artifact/commons-codec/commons-codec
    implementation("commons-codec:commons-codec:1.16.0")
    testImplementation("io.ktor:ktor-server-test-host-jvm:2.3.8")

    // Jsoup
    implementation("org.jsoup:jsoup:1.17.2")
    // 将 Markdown 转 HTML
    implementation("org.commonmark:commonmark:0.21.0")

    // 接口访问速率限制
    implementation("io.ktor:ktor-server-rate-limit:$ktor_version")


    // 用于处理 Redis Lettuce Kotlin 相关异步操作
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinx_coroutines_version}")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-reactive
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${kotlinx_coroutines_version}")


}

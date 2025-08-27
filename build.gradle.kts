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

val mariadb_version: String by project
val mysql_version: String by project

val qcloud_version: String by project

val pinyin4j_version: String by project

val commons_codec_version: String by project

val jackson_module_kotlin_version: String by project

val jsoup_version: String by project

val commonmark_version: String by project

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
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson_module_kotlin_version")

    implementation("io.ktor:ktor-server-thymeleaf:$ktor_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    // MariaDB 驱动
    implementation("org.mariadb.jdbc:mariadb-java-client:$mariadb_version")
    // https://mvnrepository.com/artifact/com.mysql/mysql-connector-j
    implementation("com.mysql:mysql-connector-j:$mysql_version")

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
    implementation("com.qcloud:cos_api:$qcloud_version")

    // https://mvnrepository.com/artifact/com.belerweb/pinyin4j
    implementation("com.belerweb:pinyin4j:$pinyin4j_version")



    implementation("io.ktor:ktor-server-netty-jvm")
//    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // https://mvnrepository.com/artifact/commons-codec/commons-codec
    implementation("commons-codec:commons-codec:$commons_codec_version")
    testImplementation("io.ktor:ktor-server-test-host-jvm:$ktor_version")

    // Jsoup
    implementation("org.jsoup:jsoup:$jsoup_version")
    // 将 Markdown 转 HTML
    implementation("org.commonmark:commonmark:$commonmark_version")

    // 接口访问速率限制
    implementation("io.ktor:ktor-server-rate-limit:$ktor_version")

    // WebSocket
    implementation("io.ktor:ktor-server-websockets:$ktor_version")

    // SSE
    implementation("io.ktor:ktor-server-sse:$ktor_version")

    // 用于处理 Redis Lettuce Kotlin 相关异步操作
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:${kotlinx_coroutines_version}")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-reactive
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-reactive:${kotlinx_coroutines_version}")


}

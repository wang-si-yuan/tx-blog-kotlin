import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.1.13.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    kotlin("jvm") version "1.2.71"
    kotlin("plugin.spring") version "1.2.71"
    kotlin("plugin.noarg") version "1.2.71"
    distribution
}

repositories {
    mavenLocal()
    maven {
        // https://docs.gradle.org/current/userguide/declaring_repositories.html
        url = uri("http://maven.aliyun.com/nexus/content/groups/public")
    }
    mavenCentral()
}

configure(listOf(rootProject)) {
    group = "io.archx"
    version = "0.0.1"
    java.sourceCompatibility = JavaVersion.VERSION_1_8

    sourceSets {
        main {
            resources.srcDirs("src/main/conf", "src/main/resources")
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
    dependencies {
        // spring web
        implementation("org.springframework.boot:spring-boot-starter-web") {
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
        }
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("org.springframework.boot:spring-boot-starter-undertow")
        implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

        // swagger 2
        implementation("io.springfox:springfox-swagger2:2.9.2") {
            exclude(group = "io.swagger", module = "swagger-annotations")
            exclude(group = "io.swagger", module = "swagger-models")
        }
        implementation("io.springfox:springfox-swagger-ui:2.9.2")
        implementation("io.swagger:swagger-annotations:1.5.21")
        implementation("io.swagger:swagger-models:1.5.21")

        // jdbc
        implementation("com.baomidou:mybatis-plus-boot-starter:3.2.0")
        runtimeOnly("mysql:mysql-connector-java")
        runtimeOnly("com.zaxxer:HikariCP:3.2.0")

        implementation("com.auth0:java-jwt:3.10.1")
        implementation("com.qiniu:qiniu-java-sdk:7.2.27")
        implementation("com.google.code.gson:gson")
        implementation("com.tinify:tinify:1.6.4") {
            exclude(group = "com.squareup.okhttp3", module = "okhttp")
        }

        implementation("com.squareup.okhttp3:okhttp:3.14.4")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }
}

// 打包配置

// 指定打包的tar包的名字，以及文件来源目录
distributions {
    create("app") {
        baseName = baseName
        contents {
            from("$buildDir/libs")
        }
    }
}

tasks {
    // 清除现有的lib目录
    register<Delete>("clearJar") {
        delete("$buildDir/libs")
        delete("$buildDir/distributions")
    }

    // 将依赖包复制到lib目录
    register<Copy>("copyJar") {
        dependsOn(named("clearJar"))

        from(configurations.compileClasspath, configurations.runtimeClasspath)
        into("$buildDir/libs/lib")
    }

    register<Copy>("copyConf") {
        dependsOn(named("copyJar"))

        from("src/main/conf") {
            include("**/*.*")
        }
        into("$buildDir/libs/config")
    }

    named<BootJar>("bootJar") {

        dependsOn(named("clearJar"))
        dependsOn(named("copyJar"))
        dependsOn(named("copyConf"))

        exclude("*.jar")

        manifest {
            // compileClasspath.get().files.joinToString(" ") { "lib/${it.name}" }
            attributes(mapOf("Manifest-Version" to 1.0, "Class-Path" to fileTree("$buildDir/libs/lib").files.joinToString(" ") { "lib/${it.name}" }))
        }
    }

    // distribution 插件的特性，以DistTar结尾
    named<Tar>("appDistTar") {
        dependsOn(named("bootJar"))
        compression = Compression.GZIP
    }

    // 定义一个task，先build 然后再打包tar包
    register("buildTar") {
        dependsOn(named("build"))
        dependsOn(named("appDistTar"))
        doLast {
            println("build success")
        }
    }
}
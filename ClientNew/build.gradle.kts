plugins {
    `java-library`
    `maven-publish`
}



repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}
dependencies {
    api("net.sf.jasperreports:jasperreports:6.17.0")
    api("org.springframework.security:spring-security-core:5.6.1")
    api("org.mindrot:jbcrypt:0.4")
    api("org.jfree:jfreechart:1.5.3")
    api("org.apache.logging.log4j:log4j-core:2.17.1")
    api("org.apache.poi:poi:5.1.0")
    api("org.apache.poi:poi-ooxml:5.1.0")
    api("org.apache.maven.plugins:maven-jar-plugin:3.2.0")
    api("com.toedter:jcalendar:1.4")
    api("org.apache.commons:commons-lang3:3.12.0")
//    api("org.kordamp.ikonli:ikonli-swing:13.2.0")
//    api("org.kordamp.ikonli:ikonli-materialdesign-pack:13.2.0")
    api("com.itextpdf:itextpdf:5.5.13.2")
    implementation("org.hibernate:hibernate-core:6.4.4.Final")
    testImplementation("org.hibernate.orm:hibernate-testing:6.4.4.Final")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("log4j:log4j:1.2.17")
    implementation("org.apache.logging.log4j:log4j-api:2.17.1")
    implementation("org.apache.logging.log4j:log4j-core:2.17.1")
    implementation("com.microsoft.sqlserver:mssql-jdbc:11.2.2.jre17")
    implementation(files("lib/libFinal8.jar"))

    api("org.kordamp.ikonli:ikonli-swing:12.3.1")
    api("org.kordamp.ikonli:ikonli-materialdesign-pack:12.3.1")
}

group = "vn.N10_PTUD_v1"
version = "0.0.1-SNAPSHOT"
description = "QuanLyHieuSach"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
    include("**/*Test.*")
}

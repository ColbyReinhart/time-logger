plugins {
	id("java")
	id("war")
}

repositories {
	mavenCentral()
}

dependencies {
	compileOnly("jakarta.servlet:jakarta.servlet-api:6.0.0")
	implementation("org.xerial:sqlite-jdbc:3.46.1.0")
	implementation("commons-io:commons-io:2.18.0")
}

war {
	setProperty("archivesBaseName", "timelogger")
}

tasks.register<Copy>("deploy") {
	group = "deployment"
	description = "Deploys WAR file to the target destination"
	dependsOn("war")
	from(tasks.named("war"))
	into(file("/opt/tomcat/webapps"))
}

tasks.register<Exec>("deployAndReload") {
	group = "deployment"
	description = "Deploys and instantly refreshes the deployed application using the tomcat manager API"
	dependsOn("deploy")
	commandLine("curl", "-s", "-u", System.getenv("MANAGER_CREDENTIALS"), "localhost:8080/manager/text/reload?path=/timelogger")
}

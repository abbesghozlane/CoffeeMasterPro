import org.gradle.api.initialization.dsl.RepositoriesMode

pluginManagement {
	repositories {
		gradlePluginPortal()
		google()
		mavenCentral()
	}
}

dependencyResolutionManagement {
	repositoriesMode.set(org.gradle.api.initialization.dsl.RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
	}
}

rootProject.name = "CoffeeMasterPro"
include(":app")

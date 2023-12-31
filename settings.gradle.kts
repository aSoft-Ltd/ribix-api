pluginManagement {
    includeBuild("../build-logic")
}

plugins {
    id("multimodule")
}

fun includeSubs(base: String, path: String = base, vararg subs: String) {
    subs.forEach {
        include(":$base-$it")
        project(":$base-$it").projectDir = File("$path/$it")
    }
}

listOf(
    "kommander", "kollections", "kase", "kash-api"
).forEach {
    includeBuild("../$it")
}

rootProject.name = "ribix-api"

// submodules
includeSubs("ribix", ".", "charts", "changes")

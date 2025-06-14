plugins {
    `kotlin-dsl`
    id("java-gradle-plugin")
}

group = "com.pivnoydevelopment.plugins"

gradlePlugin {
    plugins {
        create("developPropertiesPlugin") {
            id = "com.pivnoydevelopment.plugins.develop_properties"
            implementationClass = "com.pivnoydevelopment.plugins.develop_properties.DevelopPropertiesPlugin"
        }
    }
}

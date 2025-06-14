package com.pivnoydevelopment.plugins.develop_properties

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.io.StringReader
import java.util.*

/**
 * Хранилище значений, прочитанных из файла develop.properties
 *
 * Плагин дает возможность получить к ним типизированный доступ в build.gradle.kts
 */
@Suppress("detekt.UnnecessaryAbstractClass")
abstract class DevelopPropertiesPluginExtension {
    var apiKey = ""
}

class DevelopPropertiesPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val extension = extensions.create<DevelopPropertiesPluginExtension>("developProperties")
            val properties = readProperties(target)

            properties?.let { writePropertiesToExtension(it, extension) }
        }
    }

    private fun readProperties(target: Project): Properties? {
        with(target) {
            val fileContent = providers
                .fileContents(rootProject.layout.projectDirectory.file("develop.properties"))
                .asText
                .orNull

            return fileContent?.let { content ->
                Properties().apply {
                    load(StringReader(content))
                }
            }
        }
    }

    private fun writePropertiesToExtension(
        properties: Properties,
        extension: DevelopPropertiesPluginExtension,
    ) {
        with(extension) {
            properties.getProperty("apiKey")?.let {
                apiKey = it
            }
        }
    }

}

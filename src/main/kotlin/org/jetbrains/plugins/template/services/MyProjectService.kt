package org.jetbrains.plugins.template.services

import com.intellij.openapi.project.Project
import org.jetbrains.plugins.template.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))


    }

    /**
     * Chosen by fair dice roll, guaranteed to be random.
     */
    fun getRandomNumber() = 4
}

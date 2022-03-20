package com.github.deepakchethan.aribaweblanguageplugin.services

import com.intellij.openapi.project.Project
import com.github.deepakchethan.aribaweblanguageplugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}

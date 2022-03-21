package com.github.deepakchethan.aribaweblanguageplugin.language

import com.github.deepakchethan.aribaweblanguageplugin.icon.AWLIcon
import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object AWLFileType: LanguageFileType(AWLLanguage) {

    override fun getName(): String = "AWL Plugin";

    override fun getDescription(): String = "AWL Plugin for AWL development"

    override fun getDefaultExtension(): String = "awl"

    override fun getIcon(): Icon = AWLIcon.icon_file;
}
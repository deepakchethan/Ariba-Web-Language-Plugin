package com.github.deepakchethan.aribaweblanguageplugin.language.psi

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLFileType
import com.github.deepakchethan.aribaweblanguageplugin.language.AWLLanguage
import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class AWLFile(viewProvider: FileViewProvider): PsiFileBase(viewProvider, AWLLanguage) {

    override fun getFileType(): FileType = AWLFileType

    override fun toString(): String = "AWL File"
}
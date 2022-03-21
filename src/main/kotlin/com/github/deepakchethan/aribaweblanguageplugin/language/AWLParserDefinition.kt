package com.github.deepakchethan.aribaweblanguageplugin.language

import com.github.deepakchethan.aribaweblanguageplugin.language.psi.AWLFile
import com.github.deepakchethan.aribaweblanguageplugin.language.psi.AWLLexer
import com.github.deepakchethan.aribaweblanguageplugin.language.psi.AWLParser
import com.intellij.lang.PsiParser
import com.intellij.lang.html.HTMLParserDefinition
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType

class AWLParserDefinition: HTMLParserDefinition() {

    override fun createLexer(project: Project?): Lexer = AWLLexer

    override fun createParser(project: Project?): PsiParser = AWLParser

    override fun getFileNodeType(): IFileElementType = IFileElementType(AWLLanguage)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = AWLFile(viewProvider)
}
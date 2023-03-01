package com.github.deepakchethan.aribaweblanguageplugin.language.parser

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLLanguage
import com.github.deepakchethan.aribaweblanguageplugin.language.AWLElementType
import com.github.deepakchethan.aribaweblanguageplugin.language.AWLFile
import com.github.deepakchethan.aribaweblanguageplugin.language.lexer.AWLLexerAdapter
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class AWLParserDefinition: ParserDefinition {
    var fileType = IFileElementType(AWLLanguage)

    override fun createLexer(project: Project?): Lexer = AWLLexerAdapter

    override fun createParser(project: Project?): PsiParser = AWLParser

    override fun getFileNodeType(): IFileElementType = fileType

    override fun getCommentTokens(): TokenSet = TokenSet.create(AWLElementType.AWL_COMMENT)

    override fun getStringLiteralElements(): TokenSet =  TokenSet.create(TokenType.WHITE_SPACE);

    override fun createElement(node: ASTNode): PsiElement = AWLElementType.createElement(node)

    override fun createFile(viewProvider: FileViewProvider): PsiFile = AWLFile(viewProvider)
}
package com.github.deepakchethan.aribaweblanguageplugin.language.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

object AWLParser: PsiParser {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        parseWithoutBuildingTree(root, builder, createAWLParsingHelper(builder))
        return builder.treeBuilt
    }

    private fun parseWithoutBuildingTree(
        root: IElementType, builder: PsiBuilder,
        htmlParsing: AWLParsingHelper
    ) {
        builder.enforceCommentTokens(TokenSet.EMPTY)
        val file = builder.mark()
        htmlParsing.parseDocument()
        file.done(root)
    }

    // to be able to manage what tags treated as single
    private fun createAWLParsingHelper(builder: PsiBuilder): AWLParsingHelper {
        return AWLParsingHelper(builder)
    }
}
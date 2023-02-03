package com.github.deepakchethan.aribaweblanguageplugin.language.formatter

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLElementType
import com.github.deepakchethan.aribaweblanguageplugin.language.AWLLanguage
import com.intellij.formatting.*
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import java.util.*


class AWLBlock(node: ASTNode,
               wrap: Wrap,
               alignment: Alignment,
               private val spacingBuilder: SpacingBuilder,
               private val indent: Indent) :
    AbstractBlock(node, wrap, alignment) {


    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun isLeaf(): Boolean = node.firstChildNode == null

    override fun getIndent(): Indent = indent

    private fun computeIndent(node: ASTNode): Indent {
        if (node.elementType == AWLElementType.AWL_TAG) {
            return Indent.getNormalIndent()
        }
        return Indent.getNoneIndent()
    }

    override fun buildChildren(): MutableList<Block> {
        val blocks: MutableList<Block> = ArrayList()
        var child = myNode.firstChildNode
        while (child != null) {
            if (child.elementType !== TokenType.WHITE_SPACE) {
                val block: Block = AWLBlock(
                    child, Wrap.createWrap(WrapType.NONE, false),
                    Alignment.createAlignment(),
                    spacingBuilder,
                    computeIndent(child)
                )
                blocks.add(block)
            }
            child = child.treeNext
        }
        return blocks
    }
}
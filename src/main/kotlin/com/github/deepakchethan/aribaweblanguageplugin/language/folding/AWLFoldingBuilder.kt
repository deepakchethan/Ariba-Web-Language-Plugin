package com.github.deepakchethan.aribaweblanguageplugin.language.folding

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLElementType
import com.github.deepakchethan.aribaweblanguageplugin.language.psi.AWLProperty
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import java.util.*


class AWLFoldingBuilder: FoldingBuilderEx(), DumbAware {

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        val literalExpressions: Collection<AWLProperty> = PsiTreeUtil.findChildrenOfType(
            root,
            AWLProperty::class.java
        )

        val descriptors: ArrayList<FoldingDescriptor> = ArrayList()

        for (literalExpression in literalExpressions) {
            val type = literalExpression.node.elementType

            if (type == AWLElementType.AWL_TAG) {
                descriptors.add(
                    FoldingDescriptor(
                        literalExpression.node,
                        TextRange(
                            literalExpression.textRange.startOffset + 1,
                            literalExpression.textRange.endOffset - 1
                        )
                    )
                )
            }
        }

        return descriptors.toArray(arrayOfNulls(descriptors.size))
    }

    override fun getPlaceholderText(node: ASTNode): String  {
        var len = node.textLength
        if (len > 8) {
            len = 8
        }
        val sb = StringBuilder()
        sb.append(node.text.subSequence(1, len)).append("...")
        return sb.toString()
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean = false
}
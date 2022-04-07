package com.github.deepakchethan.aribaweblanguageplugin.language.highlighter

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLElementType
import com.github.deepakchethan.aribaweblanguageplugin.language.lexer.AWLLexerAdapter
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.containers.MultiMap

class AWLSyntaxHighlighter: SyntaxHighlighterBase() {

    companion object {
        val ourMap = MultiMap.create<IElementType, TextAttributesKey>()
        
        init {
            ourMap.putValue(AWLElementType.AWL_TAG_CHARACTERS, AWLHighlighterColors.AWL_TAG)

            for (type in ContainerUtil.ar(
                AWLElementType.AWL_COMMENT_START, AWLElementType.AWL_COMMENT_END, AWLElementType.AWL_COMMENT_CHARACTERS,
                AWLElementType.AWL_CONDITIONAL_COMMENT_END, AWLElementType.AWL_CONDITIONAL_COMMENT_END_START,
                AWLElementType.AWL_CONDITIONAL_COMMENT_START, AWLElementType.AWL_CONDITIONAL_COMMENT_START_END
            )) {
                ourMap.putValue(type, AWLHighlighterColors.AWL_COMMENT)
            }

            for (type in ContainerUtil.ar(
                AWLElementType.AWL_START_TAG_START,
                AWLElementType.AWL_END_TAG_START,
                AWLElementType.AWL_TAG_END,
                AWLElementType.AWL_EMPTY_ELEMENT_END,
                AWLElementType.TAG_WHITE_SPACE
            )) {
                ourMap.putValue(type, AWLHighlighterColors.AWL_TAG)
            }

            ourMap.putValues(
                AWLElementType.AWL_NAME,
                listOf(AWLHighlighterColors.AWL_TAG, AWLHighlighterColors.AWL_TAG_NAME)
            )

            ourMap.putValues(
                AWLElementType.AWL_ATTRIBUTE_NAME,
                listOf(AWLHighlighterColors.AWL_TAG, AWLHighlighterColors.AWL_ATTRIBUTE_NAME)
            )

            for (type in ContainerUtil.ar(
                AWLElementType.AWL_EQ,
                AWLElementType.AWL_ATTRIBUTE_VALUE_TOKEN,
                AWLElementType.AWL_ATTRIBUTE_VALUE_START_DELIMITER,
                AWLElementType.AWL_ATTRIBUTE_VALUE_END_DELIMITER
            )) {
                ourMap.putValues(
                    type,
                    listOf(AWLHighlighterColors.AWL_TAG, AWLHighlighterColors.AWL_ATTRIBUTE_VALUE)
                )
            }

            for (type in ContainerUtil.ar(
                AWLElementType.AWL_PI_START,
                AWLElementType.AWL_PI_END,
                AWLElementType.AWL_DOCTYPE_START,
                AWLElementType.AWL_DOCTYPE_END,
                AWLElementType.AWL_DOCTYPE_PUBLIC
            )) {
                ourMap.putValue(type, AWLHighlighterColors.AWL_TAG)
            }

            ourMap.putValues(
                AWLElementType.AWL_PI_TARGET,
                listOf(AWLHighlighterColors.AWL_TAG, AWLHighlighterColors.AWL_TAG_NAME)
            )

            ourMap.putValue(AWLElementType.AWL_CHAR_ENTITY_REF, AWLHighlighterColors.AWL_ENTITY_REFERENCE)
            ourMap.putValue(AWLElementType.AWL_ENTITY_REF_TOKEN, AWLHighlighterColors.AWL_ENTITY_REFERENCE)

            ourMap.putValue(AWLElementType.AWL_BAD_CHARACTER, HighlighterColors.BAD_CHARACTER)
        }
    }

    override fun getHighlightingLexer(): Lexer = AWLLexerAdapter

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey?>? {
        synchronized(javaClass) {
            return pack(
                AWLHighlighterColors.AWL_CODE,
                ourMap[tokenType].toArray<TextAttributesKey> { size -> arrayOfNulls<TextAttributesKey>(size) }
            )
        }
    }
}
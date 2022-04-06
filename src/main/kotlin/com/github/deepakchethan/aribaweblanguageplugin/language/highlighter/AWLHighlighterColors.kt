package com.github.deepakchethan.aribaweblanguageplugin.language.highlighter

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.CodeInsightColors
import com.intellij.openapi.editor.colors.TextAttributesKey

class AWLHighlighterColors {
    companion object {
        val AWL_PROLOGUE = TextAttributesKey.createTextAttributesKey("AWL_PROLOGUE", HighlighterColors.TEXT)
        val AWL_COMMENT =
            TextAttributesKey.createTextAttributesKey("AWL_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val AWL_TAG = TextAttributesKey.createTextAttributesKey("AWL_TAG", DefaultLanguageHighlighterColors.MARKUP_TAG)
        val AWL_TAG_NAME =
            TextAttributesKey.createTextAttributesKey("AWL_TAG_NAME", DefaultLanguageHighlighterColors.MARKUP_TAG)
        val AWL_ATTRIBUTE_NAME = TextAttributesKey.createTextAttributesKey(
            "AWL_ATTRIBUTE_NAME",
            DefaultLanguageHighlighterColors.MARKUP_ATTRIBUTE
        )
        val AWL_ATTRIBUTE_VALUE =
            TextAttributesKey.createTextAttributesKey("AWL_ATTRIBUTE_VALUE", DefaultLanguageHighlighterColors.STRING)
        val AWL_ENTITY_REFERENCE = TextAttributesKey.createTextAttributesKey(
            "AWL_ENTITY_REFERENCE",
            DefaultLanguageHighlighterColors.MARKUP_ENTITY
        )
        val AWL_CODE = TextAttributesKey.createTextAttributesKey("AWL_CODE", HighlighterColors.TEXT)
        val MATCHED_TAG_NAME =
            TextAttributesKey.createTextAttributesKey("MATCHED_TAG_NAME", CodeInsightColors.MATCHED_BRACE_ATTRIBUTES)
    }

}
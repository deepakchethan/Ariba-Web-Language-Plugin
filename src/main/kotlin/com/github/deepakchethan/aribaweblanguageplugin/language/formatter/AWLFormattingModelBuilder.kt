package com.github.deepakchethan.aribaweblanguageplugin.language.formatter

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLElementType
import com.github.deepakchethan.aribaweblanguageplugin.language.AWLLanguage
import com.intellij.formatting.*
import com.intellij.psi.codeStyle.CodeStyleSettings


class AWLFormattingModelBuilder: FormattingModelBuilder {

    private fun createSpaceBuilder(settings: CodeStyleSettings): SpacingBuilder {
        return SpacingBuilder(settings, AWLLanguage)
            .around(AWLElementType.AWL_EQ)
            .spaceIf(settings.getCommonSettings(AWLLanguage.id).SPACE_AROUND_ASSIGNMENT_OPERATORS)
    }

    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val codeStyleSettings = formattingContext.codeStyleSettings
        return FormattingModelProvider
            .createFormattingModelForPsiFile(
                formattingContext.containingFile,
                AWLBlock(
                    formattingContext.node,
                    Wrap.createWrap(WrapType.NONE, false),
                    Alignment.createAlignment(),
                    createSpaceBuilder(codeStyleSettings),
                    Indent.getNoneIndent()
                ),
                codeStyleSettings
            )
    }
}
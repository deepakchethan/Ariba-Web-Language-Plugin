package com.github.deepakchethan.aribaweblanguageplugin.language.codestyle

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLLanguage
import com.intellij.application.options.IndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.CodeStyleSettingsCustomizable
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider


class AWLLanguageCodeStyleSettingsProvider: LanguageCodeStyleSettingsProvider() {

    override fun getLanguage(): Language = AWLLanguage

    override fun customizeSettings(
        consumer: CodeStyleSettingsCustomizable,
        settingsType: SettingsType
    ) {
        when (settingsType) {
            SettingsType.SPACING_SETTINGS -> {
                consumer.showStandardOptions(
                    CodeStyleSettingsCustomizable.SpacingOption.SPACE_AROUND_ASSIGNMENT_OPERATORS.toString())
            }
            SettingsType.INDENT_SETTINGS -> {
                consumer.showStandardOptions(
                    CodeStyleSettingsCustomizable.IndentOption.TAB_SIZE.toString(),
                    CodeStyleSettingsCustomizable.IndentOption.INDENT_SIZE.toString())
            }
            SettingsType.BLANK_LINES_SETTINGS -> {
                consumer.showStandardOptions(
                    CodeStyleSettingsCustomizable.BlankLinesOption.KEEP_BLANK_LINES_IN_CODE.toString()
                )
            }
        }
    }

    override fun getIndentOptionsEditor(): IndentOptionsEditor {
        return IndentOptionsEditor()
    }

    override fun getCodeSample(settingsType: SettingsType): String =
    """
        <!--
        *        Sample comment
        -->
        <HTML>
            <body>
                <AWForm action="submitForm()">
                    <AWConditional ifTrue="true">
                        <div> This is form </div>
                    <AWElse/>
                        <div> This is else block </div>
                    </AWConditional>
                </AWForm>
            </body>
        </html>
    """
}
package com.github.deepakchethan.aribaweblanguageplugin.language.codestyle

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.IndentOptionsEditor
import com.intellij.psi.codeStyle.CodeStyleConfigurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider
import com.intellij.psi.codeStyle.CustomCodeStyleSettings


class AWLCodeStyleProvider: CodeStyleSettingsProvider() {

    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings? {
        return AWLCodeStyleSettings(settings)
    }

    override fun getConfigurableDisplayName(): String = "AWL"

    override fun createConfigurable(
        settings: CodeStyleSettings,
        modelSettings: CodeStyleSettings
    ): CodeStyleConfigurable {
        return object : CodeStyleAbstractConfigurable(settings, modelSettings, this.configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel {
                return AWLCustomCodeStyleMainPanel(currentSettings, settings)
            }
        }
    }

    private class AWLCustomCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings) :
        AWLCodeStyleMainPanel(currentSettings, settings)
}
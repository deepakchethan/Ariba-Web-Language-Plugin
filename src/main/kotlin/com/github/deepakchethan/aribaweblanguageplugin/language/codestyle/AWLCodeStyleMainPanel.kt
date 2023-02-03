package com.github.deepakchethan.aribaweblanguageplugin.language.codestyle

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLLanguage
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.psi.codeStyle.CodeStyleSettings


open class AWLCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings):
    TabbedLanguageCodeStylePanel (AWLLanguage, currentSettings, settings) {

    override fun initTabs(settings: CodeStyleSettings) {
        addIndentOptionsTab(settings)
        addSpacesTab(settings)
    }
}
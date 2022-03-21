package com.github.deepakchethan.aribaweblanguageplugin.language.highlighter

import com.intellij.lang.html.HtmlSyntaxHighlighterFactory
import com.intellij.openapi.fileTypes.SyntaxHighlighter

class AWLSyntaxHighlighterFactory: HtmlSyntaxHighlighterFactory() {
    override fun createHighlighter(): SyntaxHighlighter = AWLSyntaxHighlighter()
}
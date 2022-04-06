package com.github.deepakchethan.aribaweblanguageplugin.language.highlighter

import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory
import com.intellij.openapi.fileTypes.SyntaxHighlighter

class AWLSyntaxHighlighterFactory: SingleLazyInstanceSyntaxHighlighterFactory() {
    override fun createHighlighter(): SyntaxHighlighter = AWLSyntaxHighlighter()
}
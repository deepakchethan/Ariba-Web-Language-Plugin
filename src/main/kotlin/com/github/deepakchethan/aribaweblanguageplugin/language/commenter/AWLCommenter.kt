package com.github.deepakchethan.aribaweblanguageplugin.language.commenter

import com.intellij.codeInsight.generation.EscapingCommenter
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.RangeMarker
import com.intellij.util.text.CharArrayUtil

class AWLCommenter: EscapingCommenter {
    private val dashes = "--"
    private val escapedDashes = "&#45;&#45;"
    private val greaterThan = ">"
    private val escapedGreaterThan = "&gt;"

    override fun getLineCommentPrefix(): String? {
        return null
    }

    override fun getBlockCommentPrefix(): String {
        return "<!-- "
    }

    override fun getBlockCommentSuffix(): String {
        return " -->"
    }

    override fun getCommentedBlockCommentPrefix(): String? {
        return "&lt;!&ndash;"
    }

    override fun getCommentedBlockCommentSuffix(): String? {
        return "&ndash;&gt;"
    }

    override fun escape(document: Document, range: RangeMarker) {
        val prefix = blockCommentPrefix
        val suffix = blockCommentSuffix
        var start = range.startOffset
        start = CharArrayUtil.shiftForward(document.charsSequence, start, " \t\n")
        val prefixStart = start
        if (CharArrayUtil.regionMatches(document.charsSequence, prefixStart, prefix)) {
            start += prefix.length
        }
        var end = range.endOffset
        if (CharArrayUtil.regionMatches(document.charsSequence, end - suffix.length, suffix)) {
            end -= suffix.length
        }
        if (start >= end) return
        for (i in end - dashes.length downTo start) {
            if (CharArrayUtil.regionMatches(document.charsSequence, i, dashes) &&
                !CharArrayUtil.regionMatches(document.charsSequence, i, suffix) &&
                !CharArrayUtil.regionMatches(document.charsSequence, i - 2, prefix)
            ) {
                document.replaceString(i, i + dashes.length, escapedDashes)
            }
        }
        if (CharArrayUtil.regionMatches(document.charsSequence, start, greaterThan)) {
            document.replaceString(start, start + greaterThan.length, escapedGreaterThan)
        }
        if (CharArrayUtil.regionMatches(document.charsSequence, prefixStart, "$prefix-")) {
            document.insertString(start, " ")
        }
        if (CharArrayUtil.regionMatches(document.charsSequence, range.endOffset - suffix.length - 1, "-$suffix")) {
            document.insertString(range.endOffset - suffix.length, " ")
        }
    }

    override fun unescape(document: Document, range: RangeMarker) {
        val start = range.startOffset
        for (i in range.endOffset downTo start) {
            if (CharArrayUtil.regionMatches(document.charsSequence, i, escapedDashes)) {
                document.replaceString(i, i + escapedDashes.length, dashes)
            }
        }
        if (CharArrayUtil.regionMatches(document.charsSequence, start, escapedGreaterThan)) {
            document.replaceString(start, start + escapedGreaterThan.length, greaterThan)
        }
    }
}
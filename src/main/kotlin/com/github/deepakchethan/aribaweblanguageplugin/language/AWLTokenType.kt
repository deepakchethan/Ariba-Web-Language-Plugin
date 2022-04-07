package com.github.deepakchethan.aribaweblanguageplugin.language

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLLanguage
import com.intellij.psi.tree.IElementType

class AWLTokenType(id: String): IElementType(id, AWLLanguage) {
    override fun toString(): String = "AWLTokenType." + super.toString()
}
package com.github.deepakchethan.aribaweblanguageplugin.language.psi

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLLanguage
import com.intellij.psi.tree.IElementType

class AWLTokenType(id: String): IElementType(id, AWLLanguage) {
    override fun toString(): String = "SimpleTokenType." + super.toString()
}
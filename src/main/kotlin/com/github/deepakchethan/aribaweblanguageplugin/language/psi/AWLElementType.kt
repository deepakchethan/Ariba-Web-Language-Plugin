package com.github.deepakchethan.aribaweblanguageplugin.language.psi

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLLanguage
import com.intellij.psi.tree.IElementType

class AWLElementType(id: String): IElementType(id, AWLLanguage) {
    companion object {
        var AWL_DOCUMENT: IElementType = IElementType("AWL_DOCUMENT", AWLLanguage);
        var AWL_TAG: IElementType = IElementType("AWL_TAG", AWLLanguage);
    }
}
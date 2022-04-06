package com.github.deepakchethan.aribaweblanguageplugin.language.psi

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLLanguage
import com.github.deepakchethan.aribaweblanguageplugin.language.psi.impl.AWLPropertyImpl
import com.intellij.lang.ASTNode
import com.intellij.lang.dtd.DTDLanguage
import com.intellij.psi.PsiElement
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet

class AWLElementType(id: String): IElementType(id, AWLLanguage) {
    companion object {
        @JvmField var DTD_FILE = IFileElementType("DTD_FILE", DTDLanguage.INSTANCE)
        @JvmField var AWL_DOCUMENT: IElementType = IAWLElementType("AWL_DOCUMENT")
        @JvmField var AWL_TAG: IElementType = IAWLElementType("AWL_TAG")
        @JvmField var AWL_PROLOG: IElementType = IAWLElementType("AWL_PROLOG")
        @JvmField var AWL_TEXT: IElementType = IAWLElementType("AWL_TEXT")
        @JvmField var AWL_DECL: IElementType = IAWLElementType("AWL_DECL")
        @JvmField var AWL_DOCTYPE: IElementType = IAWLElementType("AWL_DOCTYPE")
        @JvmField var AWL_ATTRIBUTE: IElementType = IAWLElementType("AWL_ATTRIBUTE")
        @JvmField var AWL_COMMENT: IElementType = IAWLElementType("AWL_COMMENT")
        @JvmField var AWL_ELEMENT_DECL: IElementType = IAWLElementType("AWL_ELEMENT_DECL")
        @JvmField var AWL_CONDITIONAL_SECTION: IElementType = IAWLElementType("AWL_CONDITIONAL_SECTION")

        @JvmField var AWL_ATTLIST_DECL: IElementType = IAWLElementType("AWL_ATTLIST_DECL")
        @JvmField var AWL_NOTATION_DECL: IElementType = IAWLElementType("AWL_NOTATION_DECL")
        @JvmField var AWL_ENTITY_DECL: IElementType = IAWLElementType("AWL_ENTITY_DECL")
        @JvmField var AWL_ELEMENT_CONTENT_SPEC: IElementType = IAWLElementType("AWL_ELEMENT_CONTENT_SPEC")
        @JvmField var AWL_ELEMENT_CONTENT_GROUP: IElementType = IAWLElementType("AWL_ELEMENT_CONTENT_GROUP")
        @JvmField var AWL_ATTRIBUTE_DECL: IElementType = IAWLElementType("AWL_ATTRIBUTE_DECL")
        @JvmField var AWL_ATTRIBUTE_VALUE: IElementType = IAWLElementType("AWL_ATTRIBUTE_VALUE")
        @JvmField var AWL_ENTITY_REF: IElementType = IAWLElementType("AWL_ENTITY_REF")
        @JvmField var AWL_ENUMERATED_TYPE: IElementType = IAWLElementType("AWL_ENUMERATED_TYPE")
        @JvmField var AWL_PROCESSING_INSTRUCTION: IElementType = IAWLElementType("AWL_PROCESSING_INSTRUCTION")
        @JvmField var AWL_CDATA: IElementType = IAWLElementType("AWL_CDATA")

        @JvmField var AWL_START_TAG_START: IElementType = IAWLElementType("AWL_START_TAG_START")
        @JvmField var AWL_END_TAG_START: IElementType = IAWLElementType("AWL_END_TAG_START")
        @JvmField var AWL_TAG_END: IElementType = IAWLElementType("AWL_TAG_END")
        @JvmField var AWL_EMPTY_ELEMENT_END: IElementType = IAWLElementType("AWL_EMPTY_ELEMENT_END")
        @JvmField var AWL_TAG_NAME: IElementType = IAWLElementType("AWL_TAG_NAME")
        @JvmField var AWL_NAME: IElementType = IAWLElementType("AWL_NAME")
        @JvmField var AWL_ATTRIBUTE_VALUE_TOKEN: IElementType = IAWLElementType("AWL_ATTRIBUTE_VALUE_TOKEN")
        @JvmField var AWL_ATTRIBUTE_VALUE_START_DELIMITER: IElementType =
            IAWLElementType("AWL_ATTRIBUTE_VALUE_START_DELIMITER")
        @JvmField var AWL_ATTRIBUTE_VALUE_END_DELIMITER: IElementType = IAWLElementType("AWL_ATTRIBUTE_VALUE_END_DELIMITER")
        @JvmField var AWL_EQ: IElementType = IAWLElementType("AWL_EQ")
        @JvmField var AWL_DATA_CHARACTERS: IElementType = IAWLElementType("AWL_DATA_CHARACTERS")
        @JvmField var AWL_TAG_CHARACTERS: IElementType = IAWLElementType("AWL_TAG_CHARACTERS")
        @JvmField var AWL_WHITE_SPACE = TokenType.WHITE_SPACE
        @JvmField var AWL_REAL_WHITE_SPACE: IElementType = IAWLElementType("AWL_WHITE_SPACE")
        @JvmField var AWL_COMMENT_START: IElementType = IAWLElementType("AWL_COMMENT_START")
        @JvmField var AWL_COMMENT_END: IElementType = IAWLElementType("AWL_COMMENT_END")
        @JvmField var AWL_COMMENT_CHARACTERS: IElementType = IAWLElementType("AWL_COMMENT_CHARACTERS")

        @JvmField var AWL_DECL_START: IElementType = IAWLElementType("AWL_DECL_START")
        @JvmField var AWL_DECL_END: IElementType = IAWLElementType("AWL_DECL_END")

        @JvmField var AWL_DOCTYPE_START: IElementType = IAWLElementType("AWL_DOCTYPE_START")
        @JvmField var AWL_DOCTYPE_END: IElementType = IAWLElementType("AWL_DOCTYPE_END")
        @JvmField var AWL_DOCTYPE_SYSTEM: IElementType = IAWLElementType("AWL_DOCTYPE_SYSTEM")
        @JvmField var AWL_DOCTYPE_PUBLIC: IElementType = IAWLElementType("AWL_DOCTYPE_PUBLIC")

        @JvmField var AWL_MARKUP_START: IElementType = IAWLElementType("AWL_MARKUP_START")
        @JvmField var AWL_MARKUP_END: IElementType = IAWLElementType("AWL_MARKUP_END")

        @JvmField var AWL_CDATA_START: IElementType = IAWLElementType("AWL_CDATA_START")
        @JvmField var AWL_CONDITIONAL_SECTION_START: IElementType = IAWLElementType("AWL_CONDITIONAL_SECTION_START")
        @JvmField var AWL_CONDITIONAL_INCLUDE: IElementType = IAWLElementType("AWL_CONDITIONAL_INCLUDE")
        @JvmField var AWL_CONDITIONAL_IGNORE: IElementType = IAWLElementType("AWL_CONDITIONAL_IGNORE")
        @JvmField var AWL_CDATA_END: IElementType = IAWLElementType("AWL_CDATA_END")
        @JvmField var AWL_CONDITIONAL_SECTION_END: IElementType = IAWLElementType("AWL_CONDITIONAL_SECTION_END")

        @JvmField var AWL_ELEMENT_DECL_START: IElementType = IAWLElementType("AWL_ELEMENT_DECL_START")
        @JvmField var AWL_NOTATION_DECL_START: IElementType = IAWLElementType("AWL_NOTATION_DECL_START")
        @JvmField var AWL_ATTLIST_DECL_START: IElementType = IAWLElementType("AWL_ATTLIST_DECL_START")
        @JvmField var AWL_ENTITY_DECL_START: IElementType = IAWLElementType("AWL_ENTITY_DECL_START")

        @JvmField var AWL_PCDATA: IElementType = IAWLElementType("AWL_PCDATA")
        @JvmField var AWL_LEFT_PAREN: IElementType = IAWLElementType("AWL_LEFT_PAREN")
        @JvmField var AWL_RIGHT_PAREN: IElementType = IAWLElementType("AWL_RIGHT_PAREN")
        @JvmField var AWL_CONTENT_EMPTY: IElementType = IAWLElementType("AWL_CONTENT_EMPTY")
        @JvmField var AWL_CONTENT_ANY: IElementType = IAWLElementType("AWL_CONTENT_ANY")
        @JvmField var AWL_QUESTION: IElementType = IAWLElementType("AWL_QUESTION")
        @JvmField var AWL_STAR: IElementType = IAWLElementType("AWL_STAR")
        @JvmField var AWL_PLUS: IElementType = IAWLElementType("AWL_PLUS")
        @JvmField var AWL_BAR: IElementType = IAWLElementType("AWL_BAR")
        @JvmField var AWL_COMMA: IElementType = IAWLElementType("AWL_COMMA")
        @JvmField var AWL_AMP: IElementType = IAWLElementType("AWL_AMP")
        @JvmField var AWL_SEMI: IElementType = IAWLElementType("AWL_SEMI")
        @JvmField var AWL_PERCENT: IElementType = IAWLElementType("AWL_PERCENT")

        @JvmField var AWL_ATT_IMPLIED: IElementType = IAWLElementType("AWL_ATT_IMPLIED")
        @JvmField var AWL_ATT_REQUIRED: IElementType = IAWLElementType("AWL_ATT_REQUIRED")
        @JvmField var AWL_ATT_FIXED: IElementType = IAWLElementType("AWL_ATT_FIXED")

        @JvmField var AWL_ENTITY_REF_TOKEN: IElementType = IAWLElementType("AWL_ENTITY_REF_TOKEN")

        @JvmField var TAG_WHITE_SPACE: IElementType = IAWLElementType("TAG_WHITE_SPACE")

        @JvmField var AWL_PI_START: IElementType = IAWLElementType("AWL_PI_START")
        @JvmField var AWL_PI_END: IElementType = IAWLElementType("AWL_PI_END")
        @JvmField var AWL_PI_TARGET: IElementType = IAWLElementType("AWL_PI_TARGET")

        @JvmField var AWL_CHAR_ENTITY_REF: IElementType = IAWLElementType("AWL_CHAR_ENTITY_REF")

        @JvmField var AWL_BAD_CHARACTER: IElementType = IAWLElementType("AWL_BAD_CHARACTER")

        @JvmField var AWL_CONDITIONAL_COMMENT_START: IElementType = IAWLElementType("CONDITIONAL_COMMENT_START")
        @JvmField var AWL_CONDITIONAL_COMMENT_START_END: IElementType = IAWLElementType("CONDITIONAL_COMMENT_START_END")
        @JvmField var AWL_CONDITIONAL_COMMENT_END_START: IElementType = IAWLElementType("CONDITIONAL_COMMENT_END_START")
        @JvmField var AWL_CONDITIONAL_COMMENT_END: IElementType = IAWLElementType("CONDITIONAL_COMMENT_END")

        @JvmField var COMMENTS = TokenSet.create(AWL_COMMENT_START, AWL_COMMENT_CHARACTERS, AWL_COMMENT_END)
        @JvmField var WHITESPACES = TokenSet.create(AWL_WHITE_SPACE)

        fun createElement(node: ASTNode): PsiElement = AWLPropertyImpl(node)
    }
}
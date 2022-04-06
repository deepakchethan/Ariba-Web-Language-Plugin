package com.github.deepakchethan.aribaweblanguageplugin.language.psi

import com.intellij.codeInsight.completion.CompletionUtilCore
import com.intellij.lang.PsiBuilder
import com.intellij.openapi.util.NlsContexts.ParsingError
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.tree.ICustomParsingType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.ILazyParseableElementType
import com.intellij.util.containers.Stack
import com.intellij.xml.util.HtmlUtil
import org.jetbrains.annotations.NonNls
import java.util.*

class AWLParsingHelper(builder: PsiBuilder) {
    @NonNls
    private val TR_TAG = "tr"

    @NonNls
    private val TD_TAG = "td"

    @NonNls
    private val TH_TAG = "th"

    @NonNls
    private val TABLE_TAG = "table"

    private var myBuilder: PsiBuilder = builder
    private val myTagNamesStack = Stack<String>()
    private val myOriginalTagNamesStack = Stack<String>()
    private val myTagMarkersStack = Stack<PsiBuilder.Marker>()

    @NonNls
    private val COMPLETION_NAME = StringUtil.toLowerCase(CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED)

    fun parseDocument() {
        val document = mark()
        while (token() === AWLElementType.AWL_COMMENT_START) {
            parseComment()
        }
        parseProlog()
        var error: PsiBuilder.Marker? = null
        while (!eof()) {
            val tt = token()
            if (tt === AWLElementType.AWL_START_TAG_START) {
                error = flushError(error)
                parseTag()
                myTagMarkersStack.clear()
                myTagNamesStack.clear()
            } else if (tt === AWLElementType.AWL_COMMENT_START) {
                error = flushError(error)
                parseComment()
            } else if (tt === AWLElementType.AWL_PI_START) {
                error = flushError(error)
                parseProcessingInstruction()
            } else if (tt === AWLElementType.AWL_CHAR_ENTITY_REF || tt === AWLElementType.AWL_ENTITY_REF_TOKEN) {
                parseReference()
            } else if (tt === AWLElementType.AWL_REAL_WHITE_SPACE || tt === AWLElementType.AWL_DATA_CHARACTERS) {
                error = flushError(error)
                advance()
            } else if (tt === AWLElementType.AWL_END_TAG_START) {
                val tagEndError = myBuilder.mark()
                advance()
                if (token() === AWLElementType.AWL_NAME) {
                    advance()
                    if (token() === AWLElementType.AWL_TAG_END) {
                        advance()
                    }
                }
                tagEndError.error("Make sure your closing tag matches something bruh!")
            } else if (hasCustomTopLevelContent()) {
                error = parseCustomTopLevelContent(error)
            } else {
                if (error == null) error = mark()
                advance()
            }
        }
        error?.error("Your top level element isn't completed!")
        document.done(AWLElementType.AWL_DOCUMENT)
    }

    private fun hasCustomTopLevelContent(): Boolean = false

    private fun parseCustomTopLevelContent(error: PsiBuilder.Marker?): PsiBuilder.Marker? = error

    private fun hasCustomTagContent(): Boolean = false

    private fun parseCustomTagContent(AWLText: PsiBuilder.Marker?): PsiBuilder.Marker? =  AWLText

    private fun flushError(error: PsiBuilder.Marker?): PsiBuilder.Marker? {
        error?.error("Unexpected token found!")
        return null
    }

    private fun parseDoctype() {
        assert(token() === AWLElementType.AWL_DOCTYPE_START) { "Doctype start expected" }
        val doctype = mark()
        advance()
        while (token() !== AWLElementType.AWL_DOCTYPE_END && !eof()) advance()
        if (eof()) {
            error("Unexpected end of file")
        } else {
            advance()
        }
        doctype.done(AWLElementType.AWL_DOCTYPE)
    }

    fun parseTag() {
        assert(token() === AWLElementType.AWL_START_TAG_START) { "Tag start expected" }
        var originalTagName: String
        var AWLText: PsiBuilder.Marker? = null
        while (!eof()) {
            val tt = token()
            if (tt === AWLElementType.AWL_START_TAG_START) {
                AWLText = terminateText(AWLText)
                val tag = mark()

                // Start tag header
                advance()
                if (token() !== AWLElementType.AWL_NAME) {
                    error("Tag name expected")
                    originalTagName = ""
                } else {
                    originalTagName = Objects.requireNonNull(myBuilder.tokenText).toString()
                    advance()
                }
                val tagName = StringUtil.toLowerCase(originalTagName)
                while (childTerminatesParentInStack(tagName)) {
                    val tagElementType = getHtmlTagElementType()
                    if (!HtmlUtil.isOptionalEndForHtmlTagL(myTagNamesStack.peek())) {
                        tag.precede().errorBefore(
                            "AWL element is not closed", tag
                        )
                    }
                    val top = closeTag()
                    top.doneBefore(tagElementType, tag)
                }
                myTagMarkersStack.push(tag)
                myTagNamesStack.push(tagName)
                myOriginalTagNamesStack.push(originalTagName)
                parseHeader(tagName)
                if (token() === AWLElementType.AWL_EMPTY_ELEMENT_END) {
                    advance()
                    doneTag(tag)
                    continue
                }
                if (token() === AWLElementType.AWL_TAG_END) {
                    advance()
                } else {
                    error("Tag start is not closed yet!")
                    doneTag(tag)
                    continue
                }
                if (isSingleTag(tagName, originalTagName)) {
                    val footer = mark()
                    while (token() === AWLElementType.AWL_REAL_WHITE_SPACE) {
                        advance()
                    }
                    if (token() === AWLElementType.AWL_END_TAG_START) {
                        advance()
                        if (token() === AWLElementType.AWL_NAME) {
                            if (tagName.equals(myBuilder.tokenText, ignoreCase = true)) {
                                advance()
                                footer.drop()
                                if (token() === AWLElementType.AWL_TAG_END) {
                                    advance()
                                }
                                doneTag(tag)
                                continue
                            }
                        }
                    }
                    footer.rollbackTo()
                    doneTag(tag)
                }
            } else if (tt === AWLElementType.AWL_PI_START) {
                AWLText = terminateText(AWLText)
                parseProcessingInstruction()
            } else if (tt === AWLElementType.AWL_ENTITY_REF_TOKEN || tt === AWLElementType.AWL_CHAR_ENTITY_REF) {
                AWLText = startText(AWLText)
                parseReference()
            } else if (tt === AWLElementType.AWL_CDATA_START) {
                AWLText = startText(AWLText)
                parseCData()
            } else if (tt === AWLElementType.AWL_COMMENT_START) {
                AWLText = startText(AWLText)
                parseComment()
            } else if (tt === AWLElementType.AWL_BAD_CHARACTER) {
                AWLText = startText(AWLText)
                val error = mark()
                advance()
                error.error("Unescaped ampersand or non-terminated character entity reference")
            } else if (tt is ICustomParsingType || tt is ILazyParseableElementType) {
                AWLText = terminateText(AWLText)
                advance()
            } else if (token() === AWLElementType.AWL_END_TAG_START) {
                AWLText = terminateText(AWLText)
                val footer = mark()
                advance()
                if (token() === AWLElementType.AWL_NAME) {
                    val endName = StringUtil.toLowerCase(
                        Objects.requireNonNull(
                            myBuilder.tokenText
                        )
                    )
                    val parentTagName = if (!myTagNamesStack.isEmpty()) myTagNamesStack.peek() else ""
                    if (parentTagName != endName && !endName.endsWith(COMPLETION_NAME)) {
                        val isOptionalTagEnd = HtmlUtil.isOptionalEndForHtmlTagL(parentTagName)
                        val hasChancesToMatch =
                            if (HtmlUtil.isOptionalEndForHtmlTagL(endName)) childTerminatesParentInStack(endName) else myTagNamesStack.contains(
                                endName
                            )
                        if (hasChancesToMatch) {
                            footer.rollbackTo()
                            if (!isOptionalTagEnd) {
                                error(
                                    "Named element isn't closed"
                                )
                            }
                            doneTag(myTagMarkersStack.peek())
                        } else {
                            advance()
                            if (token() === AWLElementType.AWL_TAG_END) advance()
                            footer.error("Make sure your closing tag matches something")
                        }
                        continue
                    }
                    advance()
                    while (token() !== AWLElementType.AWL_TAG_END && token() !== AWLElementType.AWL_START_TAG_START && token() !== AWLElementType.AWL_END_TAG_START && !eof()) {
                        error("Unexpected token!")
                        advance()
                    }
                } else {
                    error("Closing tag name is missing!")
                }
                footer.drop()
                if (token() === AWLElementType.AWL_TAG_END) {
                    advance()
                } else {
                    error("Closing tag is not done")
                }
                if (hasTags()) doneTag(myTagMarkersStack.peek())
            } else if ((token() === AWLElementType.AWL_REAL_WHITE_SPACE || token() === AWLElementType.AWL_DATA_CHARACTERS) && !hasTags()) {
                AWLText = terminateText(AWLText)
                advance()
            } else if (hasCustomTagContent()) {
                AWLText = parseCustomTagContent(AWLText)
            } else {
                AWLText = startText(AWLText)
                advance()
            }
        }
        terminateText(AWLText)
        while (hasTags()) {
            val tagName = myTagNamesStack.peek()
            if (!HtmlUtil.isOptionalEndForHtmlTagL(tagName) && "html" != tagName && "body" != tagName) {
                error("Named element is not closed")
            }
            doneTag(myTagMarkersStack.peek())
        }
    }

    private fun isSingleTag(tagName: String, originalTagName: String): Boolean {
        return HtmlUtil.isSingleHtmlTagL(tagName)
    }

    private fun hasTags(): Boolean {
        return !myTagNamesStack.isEmpty()
    }

    private fun closeTag(): PsiBuilder.Marker {
        myTagNamesStack.pop()
        myOriginalTagNamesStack.pop()
        return myTagMarkersStack.pop()
    }

    private fun peekTagName(): String? {
        return myTagNamesStack.peek()
    }

    private fun peekTagMarker(): PsiBuilder.Marker? {
        return myTagMarkersStack.peek()
    }

    private fun tagLevel(): Int {
        return myTagNamesStack.size
    }

    private fun doneTag(tag: PsiBuilder.Marker) {
        tag.done(getHtmlTagElementType())
        val tagName = myTagNamesStack.peek()
        closeTag()
        val parentTagName = if (hasTags()) myTagNamesStack.peek() else ""
        val isInlineTagContainer = HtmlUtil.isInlineTagContainerL(parentTagName)
        val isOptionalTagEnd = HtmlUtil.isOptionalEndForHtmlTagL(parentTagName)
        if (isInlineTagContainer && HtmlUtil.isHtmlBlockTagL(tagName) && isOptionalTagEnd && !HtmlUtil.isPossiblyInlineTag(
                tagName
            )
        ) {
            val tagElementType = getHtmlTagElementType()
            val top = closeTag()
            top.doneBefore(tagElementType, tag)
        }
    }

    private fun getHtmlTagElementType(): IElementType {
        return AWLElementType.AWL_TAG
    }

    private fun parseHeader(tagName: String) {
        val freeMakerTag = !tagName.isEmpty() && '#' == tagName[0]
        do {
            val tt = token()
            if (freeMakerTag) {
                if (tt === AWLElementType.AWL_EMPTY_ELEMENT_END || tt === AWLElementType.AWL_TAG_END || tt === AWLElementType.AWL_END_TAG_START || tt === AWLElementType.AWL_START_TAG_START) break
                advance()
            } else {
                if (tt === AWLElementType.AWL_NAME) {
                    parseAttribute()
                } else if (tt === AWLElementType.AWL_CHAR_ENTITY_REF || tt === AWLElementType.AWL_ENTITY_REF_TOKEN) {
                    parseReference()
                } else {
                    break
                }
            }
        } while (!eof())
    }

    private fun childTerminatesParentInStack(childName: String): Boolean {
        val isCell = TD_TAG == childName || TH_TAG == childName
        val isRow = TR_TAG == childName
        val isStructure = isStructure(childName)
        for (i in myTagNamesStack.indices.reversed()) {
            val parentName = myTagNamesStack[i]
            val isParentTable = TABLE_TAG == parentName
            val isParentStructure = isStructure(parentName)
            if (isCell && (TR_TAG == parentName || isParentStructure || isParentTable) || isRow && (isParentStructure || isParentTable) || isStructure && isParentTable) {
                return false
            }
            if ("li" == childName && ("ul" == parentName || "ol" == parentName)) {
                return false
            }
            if ("dl" == parentName && ("dd" == childName || "dt" == childName)) {
                return false
            }
            if (HtmlUtil.canTerminate(childName, parentName)) {
                return true
            }
        }
        return false
    }

    private fun isStructure(childName: String): Boolean {
        return "thead" == childName || "tbody" == childName || "tfoot" == childName
    }

    private fun startText(AWLText: PsiBuilder.Marker?): PsiBuilder.Marker {
        var awlText = AWLText
        if (AWLText == null) {
            awlText = mark()
        }
        return awlText!!
    }

    private fun getBuilder(): PsiBuilder? {
        return myBuilder
    }

    private fun mark(): PsiBuilder.Marker {
        return myBuilder.mark()
    }

    private fun terminateText(AWLText: PsiBuilder.Marker?): PsiBuilder.Marker? {
        AWLText?.done(AWLElementType.AWL_TEXT)
        return null
    }

    private fun parseCData() {
        assert(token() === AWLElementType.AWL_CDATA_START)
        val cdata = mark()
        while (token() !== AWLElementType.AWL_CDATA_END && !eof()) {
            advance()
        }
        if (!eof()) {
            advance()
        }
        cdata.done(AWLElementType.AWL_CDATA)
    }

    private fun parseComment() {
        val comment = mark()
        advance()
        while (true) {
            val tt = token()
            if (tt === AWLElementType.AWL_COMMENT_CHARACTERS || tt === AWLElementType.AWL_CONDITIONAL_COMMENT_START || tt === AWLElementType.AWL_CONDITIONAL_COMMENT_START_END || tt === AWLElementType.AWL_CONDITIONAL_COMMENT_END_START || tt === AWLElementType.AWL_CONDITIONAL_COMMENT_END) {
                advance()
                continue
            }
            if (tt === AWLElementType.AWL_ENTITY_REF_TOKEN || tt === AWLElementType.AWL_CHAR_ENTITY_REF) {
                parseReference()
                continue
            }
            if (tt === AWLElementType.AWL_BAD_CHARACTER) {
                val error = mark()
                advance()
                error.error("Bad character found mate")
                continue
            }
            if (tt === AWLElementType.AWL_COMMENT_END) {
                advance()
            }
            break
        }
        comment.done(AWLElementType.AWL_COMMENT)
    }

    private fun parseReference() {
        if (token() === AWLElementType.AWL_CHAR_ENTITY_REF) {
            advance()
        } else if (token() === AWLElementType.AWL_ENTITY_REF_TOKEN) {
            val ref = mark()
            advance()
            ref.done(AWLElementType.AWL_ENTITY_REF)
        } else {
            assert(false) { "Unexpected token" }
        }
    }

    private fun parseAttribute() {
        assert(token() === AWLElementType.AWL_NAME)
        val att = mark()
        advance()
        if (token() === AWLElementType.AWL_EQ) {
            advance()
            parseAttributeValue()
        }
        att.done(AWLElementType.AWL_ATTRIBUTE)
    }

    private fun parseAttributeValue() {
        val attValue = mark()
        if (token() === AWLElementType.AWL_ATTRIBUTE_VALUE_START_DELIMITER) {
            while (true) {
                val tt = token()
                if (tt == null || tt === AWLElementType.AWL_ATTRIBUTE_VALUE_END_DELIMITER || tt === AWLElementType.AWL_END_TAG_START || tt === AWLElementType.AWL_EMPTY_ELEMENT_END || tt === AWLElementType.AWL_START_TAG_START) {
                    break
                }
                if (tt === AWLElementType.AWL_BAD_CHARACTER) {
                    val error = mark()
                    advance()
                    error.error("Unescaped ampersand or non-terminated character entity reference")
                } else if (tt === AWLElementType.AWL_ENTITY_REF_TOKEN) {
                    parseReference()
                } else {
                    advance()
                }
            }
            if (token() === AWLElementType.AWL_ATTRIBUTE_VALUE_END_DELIMITER) {
                advance()
            } else {
                error("Attribute value is not closed")
            }
        } else {
            if (token() !== AWLElementType.AWL_TAG_END && token() !== AWLElementType.AWL_EMPTY_ELEMENT_END) {
                advance() // Single token att value
            }
        }
        attValue.done(AWLElementType.AWL_ATTRIBUTE_VALUE)
    }

    private fun parseProlog() {
        while (true) {
            val tt = token()
            if (tt === AWLElementType.AWL_COMMENT_START) {
                parseComment()
            } else if (tt === AWLElementType.AWL_REAL_WHITE_SPACE) {
                advance()
            } else {
                break
            }
        }
        val prolog = mark()
        while (true) {
            val tt = token()
            if (tt === AWLElementType.AWL_PI_START) {
                parseProcessingInstruction()
            } else if (tt === AWLElementType.AWL_DOCTYPE_START) {
                parseDoctype()
            } else if (tt === AWLElementType.AWL_COMMENT_START) {
                parseComment()
            } else if (tt === AWLElementType.AWL_REAL_WHITE_SPACE) {
                advance()
            } else {
                break
            }
        }
        prolog.done(AWLElementType.AWL_PROLOG)
    }

    private fun parseProcessingInstruction() {
        assert(token() === AWLElementType.AWL_PI_START)
        val pi = mark()
        advance()
        if (token() === AWLElementType.AWL_NAME || token() === AWLElementType.AWL_PI_TARGET) {
            advance()
        }
        while (token() === AWLElementType.AWL_NAME) {
            advance()
            if (token() === AWLElementType.AWL_EQ) {
                advance()
            } else {
                error("Expected attribute equal sign")
            }
            parseAttributeValue()
        }
        if (token() === AWLElementType.AWL_PI_END) {
            advance()
        } else {
            error("We have an unterminated processing exception that needs some fixing")
        }
        pi.done(AWLElementType.AWL_PROCESSING_INSTRUCTION)
    }

    private fun token(): IElementType? {
        return myBuilder.tokenType
    }

    private fun eof(): Boolean {
        return myBuilder.eof()
    }

    private fun advance() {
        myBuilder.advanceLexer()
    }

    private fun error(message: @ParsingError String) {
        myBuilder.error(message)
    }
}
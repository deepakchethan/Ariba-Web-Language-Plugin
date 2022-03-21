package com.github.deepakchethan.aribaweblanguageplugin.language.psi

import com.intellij.codeInsight.completion.CompletionUtilCore
import com.intellij.lang.PsiBuilder
import com.intellij.lang.html.HtmlParsing
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.tree.ICustomParsingType
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.ILazyParseableElementType
import com.intellij.psi.xml.XmlElementType
import com.intellij.psi.xml.XmlTokenType
import com.intellij.util.containers.Stack
import com.intellij.xml.psi.XmlPsiBundle
import com.intellij.xml.util.HtmlUtil
import java.util.*

class AWLParsing(builder: PsiBuilder): HtmlParsing(builder) {
    private val tr = "tr"
    private val td = "td"
    private val th = "th"
    private val table = "table"

    private var myBuilder: PsiBuilder = builder
    private val myTagNamesStack = Stack<String>()
    private val myOriginalTagNamesStack = Stack<String>()
    private val myTagMarkersStack = Stack<PsiBuilder.Marker>()
    
    private val COMPLETION_NAME = StringUtil.toLowerCase(CompletionUtilCore.DUMMY_IDENTIFIER_TRIMMED)

    override fun parseDocument() {
        val document = mark()
        while (token() === XmlTokenType.XML_COMMENT_START) {
            parseComment()
        }
        var error: PsiBuilder.Marker? = null
        while (!eof()) {
            val tt = token()
            if (tt === XmlTokenType.XML_START_TAG_START) {
                error = flushError(error)
                parseTag()
                myTagMarkersStack.clear()
                myTagNamesStack.clear()
            } else if (tt === XmlTokenType.XML_COMMENT_START) {
                error = flushError(error)
                parseComment()
            } else if (tt === XmlTokenType.XML_PI_START) {
                error = flushError(error)
                parseProcessingInstruction()
            } else if (tt === XmlTokenType.XML_CHAR_ENTITY_REF || tt === XmlTokenType.XML_ENTITY_REF_TOKEN) {
                parseReference()
            } else if (tt === XmlTokenType.XML_REAL_WHITE_SPACE || tt === XmlTokenType.XML_DATA_CHARACTERS) {
                error = flushError(error)
                advance()
            } else if (tt === XmlTokenType.XML_END_TAG_START) {
                val tagEndError = myBuilder.mark()
                advance()
                if (token() === XmlTokenType.XML_NAME) {
                    advance()
                    if (token() === XmlTokenType.XML_TAG_END) {
                        advance()
                    }
                }
                tagEndError.error("Random closing tag dude, remove it!")
            } else if (hasCustomTopLevelContent()) {
                error = parseCustomTopLevelContent(error)
            } else {
                if (error == null) error = mark()
                advance()
            }
        }
        error?.error("Parsing is not complete on the top level element :(")
        document.done(AWLElementType.AWL_DOCUMENT)
    }

    override fun parseTag() {
        assert(token() === XmlTokenType.XML_START_TAG_START) { "Tag start expected" }
        var originalTagName: String
        var xmlText: PsiBuilder.Marker? = null
        while (!eof()) {
            val tt = token()
            if (tt === XmlTokenType.XML_START_TAG_START) {
                xmlText = terminateText(xmlText)
                val tag = mark()

                // Start tag header
                advance()
                if (token() !== XmlTokenType.XML_NAME) {
                    error("We need a tag name, add it right now!")
                    originalTagName = ""
                } else {
                    originalTagName = Objects.requireNonNull(myBuilder.tokenText).toString()
                    advance()
                }
                val tagName = StringUtil.toLowerCase(originalTagName)
                while (childTerminatesParentInStack(tagName)) {
                    val tagElementType = getAWLTagElementType()
                    if (!HtmlUtil.isOptionalEndForHtmlTagL(myTagNamesStack.peek())) {
                        tag.precede().errorBefore(
                            XmlPsiBundle.message(
                                "xml.parsing.named.element.is.not.closed",
                                myOriginalTagNamesStack.peek()
                            ), tag
                        )
                    }
                    val top = closeTag()
                    top.doneBefore(tagElementType, tag)
                }
                myTagMarkersStack.push(tag)
                myTagNamesStack.push(tagName)
                myOriginalTagNamesStack.push(originalTagName)
                parseHeader(tagName)
                if (token() === XmlTokenType.XML_EMPTY_ELEMENT_END) {
                    advance()
                    doneTag(tag)
                    continue
                }
                if (token() === XmlTokenType.XML_TAG_END) {
                    advance()
                } else {
                    error(XmlPsiBundle.message("xml.parsing.tag.start.is.not.closed"))
                    doneTag(tag)
                    continue
                }
                if (isSingleTag(tagName, originalTagName)) {
                    val footer = mark()
                    while (token() === XmlTokenType.XML_REAL_WHITE_SPACE) {
                        advance()
                    }
                    if (token() === XmlTokenType.XML_END_TAG_START) {
                        advance()
                        if (token() === XmlTokenType.XML_NAME) {
                            if (tagName.equals(myBuilder.tokenText, ignoreCase = true)) {
                                advance()
                                footer.drop()
                                if (token() === XmlTokenType.XML_TAG_END) {
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
            } else if (tt === XmlTokenType.XML_PI_START) {
                xmlText = terminateText(xmlText)
                parseProcessingInstruction()
            } else if (tt === XmlTokenType.XML_ENTITY_REF_TOKEN || tt === XmlTokenType.XML_CHAR_ENTITY_REF) {
                xmlText = startText(xmlText)
                parseReference()
            } else if (tt === XmlTokenType.XML_CDATA_START) {
                xmlText = startText(xmlText)
                parseCData()
            } else if (tt === XmlTokenType.XML_COMMENT_START) {
                xmlText = startText(xmlText)
                parseComment()
            } else if (tt === XmlTokenType.XML_BAD_CHARACTER) {
                xmlText = startText(xmlText)
                val error = mark()
                advance()
                error.error(XmlPsiBundle.message("xml.parsing.unescaped.ampersand.or.nonterminated.character.entity.reference"))
            } else if (tt is ICustomParsingType || tt is ILazyParseableElementType) {
                xmlText = terminateText(xmlText)
                advance()
            } else if (token() === XmlTokenType.XML_END_TAG_START) {
                xmlText = terminateText(xmlText)
                val footer = mark()
                advance()
                if (token() === XmlTokenType.XML_NAME) {
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
                                    XmlPsiBundle.message(
                                        "xml.parsing.named.element.is.not.closed",
                                        myOriginalTagNamesStack.peek()
                                    )
                                )
                            }
                            doneTag(myTagMarkersStack.peek())
                        } else {
                            advance()
                            if (token() === XmlTokenType.XML_TAG_END) advance()
                            footer.error(XmlPsiBundle.message("xml.parsing.closing.tag.matches.nothing"))
                        }
                        continue
                    }
                    advance()
                    while (token() !== XmlTokenType.XML_TAG_END && token() !== XmlTokenType.XML_START_TAG_START && token() !== XmlTokenType.XML_END_TAG_START && !eof()) {
                        error(XmlPsiBundle.message("xml.parsing.unexpected.token"))
                        advance()
                    }
                } else {
                    error(XmlPsiBundle.message("xml.parsing.closing.tag.name.missing"))
                }
                footer.drop()
                if (token() === XmlTokenType.XML_TAG_END) {
                    advance()
                } else {
                    error(XmlPsiBundle.message("xml.parsing.closing.tag.is.not.done"))
                }
                if (hasTags()) doneTag(myTagMarkersStack.peek())
            } else if ((token() === XmlTokenType.XML_REAL_WHITE_SPACE || token() === XmlTokenType.XML_DATA_CHARACTERS) && !hasTags()) {
                xmlText = terminateText(xmlText)
                advance()
            } else if (hasCustomTagContent()) {
                xmlText = parseCustomTagContent(xmlText)
            } else {
                xmlText = startText(xmlText)
                advance()
            }
        }
        terminateText(xmlText)
        while (hasTags()) {
            val tagName = myTagNamesStack.peek()
            if (!HtmlUtil.isOptionalEndForHtmlTagL(tagName) && "html" != tagName && "body" != tagName) {
                error(XmlPsiBundle.message("xml.parsing.named.element.is.not.closed", myOriginalTagNamesStack.peek()))
            }
            doneTag(myTagMarkersStack.peek())
        }
    }

    private fun doneTag(tag: PsiBuilder.Marker) {
        tag.done(getAWLTagElementType())
        val tagName = myTagNamesStack.peek()
        closeTag()
        val parentTagName = if (hasTags()) myTagNamesStack.peek() else ""
        val isInlineTagContainer = HtmlUtil.isInlineTagContainerL(parentTagName)
        val isOptionalTagEnd = HtmlUtil.isOptionalEndForHtmlTagL(parentTagName)
        if (isInlineTagContainer && HtmlUtil.isHtmlBlockTagL(tagName) && isOptionalTagEnd && !HtmlUtil.isPossiblyInlineTag(
                tagName
            )
        ) {
            val tagElementType = getAWLTagElementType()
            val top = closeTag()
            top.doneBefore(tagElementType, tag)
        }
    }

    private fun getAWLTagElementType(): IElementType {
        return AWLElementType.AWL_TAG
    }

    private fun parseHeader(tagName: String) {
        val freeMakerTag = tagName.isNotEmpty() && '#' == tagName[0]
        do {
            val tt = token()
            if (freeMakerTag) {
                if (tt === XmlTokenType.XML_EMPTY_ELEMENT_END || tt === XmlTokenType.XML_TAG_END || tt === XmlTokenType.XML_END_TAG_START || tt === XmlTokenType.XML_START_TAG_START) break
                advance()
            } else {
                if (tt === XmlTokenType.XML_NAME) {
                    parseAttribute()
                } else if (tt === XmlTokenType.XML_CHAR_ENTITY_REF || tt === XmlTokenType.XML_ENTITY_REF_TOKEN) {
                    parseReference()
                } else {
                    break
                }
            }
        } while (!eof())
    }

    private fun childTerminatesParentInStack(childName: String): Boolean {
        val isCell = td == childName || th == childName
        val isRow = tr == childName
        val isStructure = isStructure(childName)
        for (i in myTagNamesStack.indices.reversed()) {
            val parentName = myTagNamesStack[i]
            val isParentTable = table == parentName
            val isParentStructure = isStructure(parentName)
            if (isCell && (tr == parentName || isParentStructure || isParentTable) || isRow && (isParentStructure || isParentTable) || isStructure && isParentTable) {
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

    override fun parseAttributeValue() {
        val attValue = mark()
        if (token() === XmlTokenType.XML_ATTRIBUTE_VALUE_START_DELIMITER) {
            while (true) {
                val tt = token()
                if (tt == null || tt === XmlTokenType.XML_ATTRIBUTE_VALUE_END_DELIMITER || tt === XmlTokenType.XML_END_TAG_START || tt === XmlTokenType.XML_EMPTY_ELEMENT_END || tt === XmlTokenType.XML_START_TAG_START) {
                    break
                }
                if (tt === XmlTokenType.XML_BAD_CHARACTER) {
                    val error = mark()
                    advance()
                    error.error(XmlPsiBundle.message("xml.parsing.unescaped.ampersand.or.nonterminated.character.entity.reference"))
                } else if (tt === XmlTokenType.XML_ENTITY_REF_TOKEN) {
                    parseReference()
                } else {
                    advance()
                }
            }
            if (token() === XmlTokenType.XML_ATTRIBUTE_VALUE_END_DELIMITER) {
                advance()
            } else {
                error("Attribute value is not closed dude, close it!")
            }
        } else {
            if (token() !== XmlTokenType.XML_TAG_END && token() !== XmlTokenType.XML_EMPTY_ELEMENT_END) {
                advance() // Single token att value
            }
        }
        attValue.done(XmlElementType.XML_ATTRIBUTE_VALUE)
    }
}
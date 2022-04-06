package com.github.deepakchethan.aribaweblanguageplugin.language.highlighter

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLFileType
import com.intellij.codeInsight.daemon.impl.tagTreeHighlighting.XmlTagTreeHighlightingColors
import com.intellij.openapi.application.ApplicationNamesInfo
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import javax.swing.Icon

class AWLColorsPage: ColorSettingsPage {
    private val attrs = arrayOf(
        AttributesDescriptor(
            "AWL code",
            AWLHighlighterColors.AWL_CODE
        ),
        AttributesDescriptor(
            "Tag",
            AWLHighlighterColors.AWL_TAG
        ),
        AttributesDescriptor(
            "Tag name",
            AWLHighlighterColors.AWL_TAG_NAME
        ),
        AttributesDescriptor(
            "Attribute name",
            AWLHighlighterColors.AWL_ATTRIBUTE_NAME
        ),
        AttributesDescriptor(
            "Attribute value",
            AWLHighlighterColors.AWL_ATTRIBUTE_NAME
        ),
        AttributesDescriptor(
            "Entity reference",
            AWLHighlighterColors.AWL_ENTITY_REFERENCE
        )
    )
    private val fullProduceName = ApplicationNamesInfo.getInstance().fullProductName

    override fun getDisplayName(): String = "AWL"

    override fun getIcon(): Icon = AWLFileType.icon

    override fun getAttributeDescriptors(): Array<AttributesDescriptor>  = attrs

    override fun getColorDescriptors(): Array<ColorDescriptor?> {
        val colorKeys = XmlTagTreeHighlightingColors.getColorKeys()
        val colorDescriptors = arrayOfNulls<ColorDescriptor>(colorKeys.size)
        for (i in colorDescriptors.indices) {
            colorDescriptors[i] = ColorDescriptor(
                String.format("Tag tree (level {%s})", i+1),
                colorKeys[i], ColorDescriptor.Kind.BACKGROUND
            )
        }
        return colorDescriptors
    }

    override fun getHighlighter(): SyntaxHighlighter {
        return AWLSyntaxHighlighter()
    }

    override fun getDemoText(): String {
        return """
        <!--
        *        Sample comment
        -->
        <HTML>
            <head>
                <title>$fullProduceName</title>
            </head>
            <body>
                <h1>$fullProduceName</h1>
                <p><br><b><IMG border=0 height=12 src="images/hg.gif" width=18 >
                    What is ${fullProduceName.replace(" ".toRegex(), "&nbsp;")}? &#x00B7; &Alpha; </b><br><br>
            </body>
        </html>
        """
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String?, TextAttributesKey?>? = null
}
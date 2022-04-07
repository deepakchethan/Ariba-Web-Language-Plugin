package com.github.deepakchethan.aribaweblanguageplugin.language.completion

import com.github.deepakchethan.aribaweblanguageplugin.language.AWLElementType
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext

class AWLCompletionContributor : CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC, PlatformPatterns.psiElement(AWLElementType.AWL_NAME),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    for (awlTag in AWLKeywords.getTagNames()) {
                        result.addElement(LookupElementBuilder.create(awlTag))
                    }
                }
            }
        );

        extend(
            CompletionType.BASIC, PlatformPatterns.psiElement(AWLElementType.AWL_ATTRIBUTE_NAME),
            object : CompletionProvider<CompletionParameters>() {
                override fun addCompletions(
                    parameters: CompletionParameters,
                    context: ProcessingContext,
                    result: CompletionResultSet
                ) {
                    for (awlTag in AWLKeywords.getTagNames()) {
                        result.addElement(LookupElementBuilder.create(awlTag))
                    }
                }
            }
        );
    }
}
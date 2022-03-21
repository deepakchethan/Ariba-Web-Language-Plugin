package com.github.deepakchethan.aribaweblanguageplugin.language.completion

import com.intellij.codeInsight.completion.HtmlCompletionContributor

class AWLCompletionContributor: HtmlCompletionContributor() {
    val awl_tags = arrayOf("AWString", "AWComponent", "ModalPageWrapper", "AWNamedTemplate")

}
package com.github.deepakchethan.aribaweblanguageplugin.language.completion

import com.google.gson.Gson

class AWLKeywords {
    companion object  {
        var awlKeywordMap: Map<String, List<String>> = HashMap()

        private fun getAWLTags(): Map<String, List<String>> {
            if (awlKeywordMap.isEmpty()) {
                val awlJsonText = this::class.java.classLoader.getResource("awl.json")?.readText()
                if (awlJsonText != null) {
                    awlKeywordMap = Gson().fromJson(awlJsonText, awlKeywordMap::class.java)
                }
            }
            return awlKeywordMap
        }

        fun getTagNames() = getAWLTags().keys
    }
}
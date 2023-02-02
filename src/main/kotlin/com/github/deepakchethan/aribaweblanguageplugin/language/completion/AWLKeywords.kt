package com.github.deepakchethan.aribaweblanguageplugin.language.completion

import com.google.gson.Gson
import java.util.*

class AWLKeywords {
    companion object  {
        var awlKeywordMap: Map<String, List<String>> = HashMap()
        var awlAttributeNames: ArrayList<String> = ArrayList()

        private fun getAWLTags(): Map<String, List<String>> {
            if (awlKeywordMap.isEmpty()) {
                val awlJsonText = this::class.java.classLoader.getResource("awl.json")?.readText()
                if (awlJsonText != null) {
                    awlKeywordMap = Gson().fromJson(awlJsonText, awlKeywordMap::class.java)
                }
            }
            return awlKeywordMap
        }

        fun getAWLAttributes(): List<String> {
            if (awlAttributeNames.isEmpty()) {
                getAWLTags()
                for (awlTagArr in awlKeywordMap.values) {
                    awlAttributeNames.addAll(awlTagArr)
                }
            }
            return awlAttributeNames
        }

        fun getTagNames() = getAWLTags().keys
    }
}
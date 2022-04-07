package com.github.deepakchethan.aribaweblanguageplugin.language.completion

import kotlinx.serialization.Serializable

@Serializable
class Keywords(var keywords: Map<String, List<String>>)
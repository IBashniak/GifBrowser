package com.example.serializable.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Original(var url: String = "", val height: Int = 0, val width: Int = 0)
package com.example.serializable.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesDTO(@SerialName("original")  var originalDTO: OriginalDTO)


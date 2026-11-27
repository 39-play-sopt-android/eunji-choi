package org.sopt.play.data.remote.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EditPostRequest (
    @SerialName("title") val title: String,
    @SerialName("content") val content: String,
)

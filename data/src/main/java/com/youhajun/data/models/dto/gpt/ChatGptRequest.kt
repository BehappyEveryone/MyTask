package com.youhajun.data.models.dto.gpt

import com.google.gson.annotations.SerializedName

data class ChatGptRequest(
    @SerializedName("messages")
    val messages: List<ChatGptMessageRequest>,

    @SerializedName("model")
    val model: String,
)
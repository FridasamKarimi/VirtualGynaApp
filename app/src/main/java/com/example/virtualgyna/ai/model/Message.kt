package com.example.virtualgyna.ai.model

data class Message(val text: String, val type: MessageType)

enum class MessageType {
    SENDER,
    RESPONSE
}

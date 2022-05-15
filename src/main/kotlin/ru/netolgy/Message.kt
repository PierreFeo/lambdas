package ru.netolgy

data class Message(
    val idMsg: Int = 0,
    val chatId: Int = 0,
    val sender: String,
    val recipientId: String,
    val message: String,
    var read: Boolean = false
)
package ru.netolgy

data class Chat(
    val ownerId: String,
    val recipientId:String,
    val chatId: Int = 0,
    val msg: MutableList<Message> = mutableListOf(),
    var dellOrNot: Boolean = false
)

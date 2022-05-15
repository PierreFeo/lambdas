package ru.netolgy

import ru.netolgy.exception.NotFoundException

class ChatService {
    private var chatId = 0
    val chats = mutableListOf<Chat>()

    //chats
    private fun createChat(element: Chat): Chat {
        chatId += 1
        val newChat = (Chat(chatId = chatId, ownerId = element.ownerId, recipientId = element.recipientId))
        chats.add(newChat)
        return newChat
    }

    fun getChats(ownerID: String): MutableList<Chat> {
        val chatsWithMsg = chats
            .filter { it.ownerId == ownerID || it.recipientId == ownerID && it.msg.isNotEmpty() }
            .ifEmpty { throw NotFoundException("нет сообщений") }
            .toMutableList()
        println("Список чатов пользователя $ownerID ")
        println(chatsWithMsg)
        return chatsWithMsg
    }

    private fun deleteChat(chatId: Int): Boolean {
        chats.remove(chats.findChatById(chatId))
        return true
    }

    fun getUnreadChatsCount(ownerID: String): Int {
        val count = chats
            .filter { it.ownerId == ownerID || it.recipientId == ownerID }
            .count { chat -> chat.msg.any { !it.read && it.recipientId == ownerID } }
        println("У пользователя ${ownerID}, непрочитанных чатов: $count")
        return count
    }

    //message

    fun createMessage(message: Message): Boolean {
        val chat = chats
            .firstOrNull {
                it.ownerId == message.sender && it.recipientId == message.recipientId ||
                        it.ownerId == message.recipientId && it.recipientId == message.sender
            }
            ?: createChat(Chat(ownerId = message.sender, recipientId = message.recipientId))
        chat.msg.add(
            Message(
                idMsg = chat.msg.size + 1,
                sender = message.sender,
                recipientId = message.recipientId,
                message = message.message,
                read = message.read,
                chatId = chatId
            )
        )
        return true
    }

    fun getMessage(chatId: Int, idMsg: Int): List<Message> {
        val msg = chats
            .findChatById(chatId).msg.filterIndexed { index, message -> index > idMsg - 2 }
            .ifEmpty { throw NotFoundException("Сообщение с id $idMsg не найдено.") }
        chats.findChatById(chatId).msg.forEach { it.read.apply { it.read = true } }
        println("Список сообщений чата с id $chatId:")
        println(msg)
        return msg
    }

    fun editMessage(chatId: Int, idMsg: Int, newText: String): Boolean {
        chats.findChatById(chatId).apply {
            msg.forEachIndexed { index, message ->
                if (message.idMsg == idMsg) {
                    msg[index] = message.copy(message = newText)
                    return true
                }
            }
            throw NotFoundException("Сообщение с id $idMsg не найдено.")
        }
    }

    fun dellMessage(chatId: Int, msgId: Int): Boolean {
        chats
            .findChatById(chatId)
            .apply {
                msg.remove(findMsg(chatId, msgId))
                    .apply { msg.ifEmpty { deleteChat(chatId) } }
                println("Сообщение с id $msgId удалено.")
            }
        return true
    }

    private fun MutableList<Chat>.findChatById(chatId: Int): Chat {
        forEach { chat: Chat ->
            if (chat.chatId == chatId) {
                return chat
            }
        }
        throw NotFoundException("Чат с id $chatId не найден.")
    }

    private val findMsg = fun(chatId: Int, msgId: Int): Message {
        return chats.findChatById(chatId).msg.firstOrNull { it.idMsg == msgId }
            ?: throw NotFoundException("Сообщение c id $msgId не найдено")
    }
}






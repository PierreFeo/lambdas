package ru.netolgy

fun main() {
    val service = ChatService()

    service.createMessage(Message(sender = "Петя", recipientId = "Оля", message = "Привет", read = false))
    service.createMessage(Message(sender = "Оля", recipientId = "Петя", message = "Пока", read = true))
    service.createMessage(Message(sender = "Ваня", recipientId = "Женя", message = "Доброе утро", read = false))
    service.createMessage(Message(sender = "Ваня", recipientId = "Женя", message = "Добрый вечер", read = false))

    service.getMessage(1,1)

    service.editMessage(1, 1, "Новый текст")

    service.dellMessage(2, 2)

    service.getUnreadChatsCount("Петя")

    service.getChats("Ваня")
}
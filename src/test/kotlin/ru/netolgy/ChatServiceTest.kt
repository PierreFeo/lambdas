package ru.netolgy

import org.junit.Assert.*
import org.junit.Test
import ru.netolgy.exception.NotFoundException

class ChatServiceTest {
    private val msg = Message(sender = "Петя", recipientId = "Оля", message = "Привет", read = false)

    @Test
    fun getChats_true() {
        val service = ChatService()
        service.createMessage(msg)
        val result = service.getChats("Петя").isNotEmpty()
        assertTrue(result)
    }

    @Test(expected = NotFoundException::class)
    fun getChats_shouldThrow() {
        val service = ChatService()
        service.getChats("Петя")
    }

    @Test
    fun getUnreadChatsCount() {
        val service = ChatService()
        val expectedId = 1
        service.createMessage(msg)
        val actualId = service.getUnreadChatsCount("Оля")
        assertEquals(expectedId, actualId)
    }

    @Test
    fun createMessage_true() {
        val service = ChatService()
        val result = service.createMessage(msg)
        assertTrue(result)
    }

    @Test
    fun getMessage_true() {
        val service = ChatService()
        service.createMessage(msg)
        val result = service.getMessage(1, 1).isNotEmpty()
        assertTrue(result)
    }

    @Test(expected = NotFoundException::class)
    fun getMessage_shouldThrow() {
        val service = ChatService()
        // service.createMessage(msg)
        service.getMessage(1, 1)
    }

    @Test
    fun editMessage_true() {
        val service = ChatService()
        service.createMessage(msg)
        val result = service.editMessage(1, 1, "Новый текст")
        assertTrue(result)
    }

    @Test(expected = NotFoundException::class)
    fun editMessage_shouldThrow() {
        val service = ChatService()
        service.createMessage(msg)
        val result = service.editMessage(1, 2, "Новый текст")
    }

    @Test
    fun dellMessage_true() {
        val service = ChatService()
        service.createMessage(msg)
        val result = service.dellMessage(1, 1)
        assertTrue(result)
    }
}
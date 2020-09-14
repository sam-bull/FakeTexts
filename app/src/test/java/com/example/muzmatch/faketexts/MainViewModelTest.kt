package com.example.muzmatch.faketexts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

class MainViewModelTest {

    companion object {
        private val TEST_TIME_IN_MILLIS = 1580551200750
        private val TEST_TIME = Calendar.getInstance().apply {
            timeInMillis = TEST_TIME_IN_MILLIS
        }
        private val TWO_SECONDS_AGO = Calendar.getInstance().apply {
            timeInMillis = TEST_TIME_IN_MILLIS - 2000
        }
        private val TWENTY_SECONDS_AGO = Calendar.getInstance().apply {
            timeInMillis = TEST_TIME_IN_MILLIS - 20000
        }
        private val TWENTY_ONE_SECONDS_AGO = Calendar.getInstance().apply {
            timeInMillis = TEST_TIME_IN_MILLIS - 21000
        }
        private val ONE_HOUR_AGO = Calendar.getInstance().apply {
            timeInMillis = TEST_TIME_IN_MILLIS - 3600000
        }
        private val OVER_ONE_HOUR_AGO = Calendar.getInstance().apply {
            timeInMillis = TEST_TIME_IN_MILLIS - 3600001
        }
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var sendMessageObserver: Observer<Event<String>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel()
        viewModel.sendMessage.observeForever(sendMessageObserver)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun onClickSendMessage() {
        viewModel.onClickSendMessage()
        verify(sendMessageObserver, times(1))
    }

    @Test
    fun sendMessage_firstMessage() {
        viewModel.sendMessage("message")

        assertEquals(viewModel.messages.size, 2)
        assertEquals(viewModel.messages.first().type, MessageType.TIMESTAMP)
        assertEquals(viewModel.messages.last().type, MessageType.SENT)
        assertTrue(viewModel.messages.last().hasTail)
    }

    @Test
    fun sendMessage_secondMessage() {
        viewModel.messages.add(Message("Timestamp", MessageType.TIMESTAMP, TWO_SECONDS_AGO))
        viewModel.messages.add(Message("message", MessageType.SENT, TWO_SECONDS_AGO, true))

        viewModel.sendMessage("message", TEST_TIME)

        assertEquals(viewModel.messages.size, 3)
        assertTrue(viewModel.messages.last().hasTail)
    }

    @Test
    fun sendMessage_onTailDelay() {
        viewModel.messages.add(Message("Timestamp", MessageType.TIMESTAMP, TWENTY_SECONDS_AGO))
        viewModel.messages.add(Message("message", MessageType.SENT, TWENTY_SECONDS_AGO, true))

        viewModel.sendMessage("message", TEST_TIME)

        assertEquals(viewModel.messages.size, 3)
        assertFalse(viewModel.messages[1].hasTail)
        assertTrue(viewModel.messages.last().hasTail)
    }

    @Test
    fun sendMessage_afterTailDelay() {
        viewModel.messages.add(Message("Timestamp", MessageType.TIMESTAMP, TWENTY_ONE_SECONDS_AGO))
        viewModel.messages.add(Message("message", MessageType.SENT, TWENTY_ONE_SECONDS_AGO, true))

        viewModel.sendMessage("message", TEST_TIME)

        assertEquals(viewModel.messages.size, 3)
        assertTrue(viewModel.messages[1].hasTail)
        assertTrue(viewModel.messages.last().hasTail)
    }

    @Test
    fun sendMessage_onTimestampDelay() {
        viewModel.messages.add(Message("Timestamp", MessageType.TIMESTAMP, ONE_HOUR_AGO))
        viewModel.messages.add(Message("message", MessageType.SENT, ONE_HOUR_AGO, true))

        viewModel.sendMessage("message", TEST_TIME)

        assertEquals(viewModel.messages.size, 3)
    }

    @Test
    fun sendMessage_afterTimestampDelay() {
        viewModel.messages.add(Message("Timestamp", MessageType.TIMESTAMP, OVER_ONE_HOUR_AGO))
        viewModel.messages.add(Message("message", MessageType.SENT, OVER_ONE_HOUR_AGO, true))

        viewModel.sendMessage("message", TEST_TIME)

        assertEquals(viewModel.messages.size, 4)
        assertTrue(viewModel.messages[1].hasTail)
        assertEquals(viewModel.messages[2].type, MessageType.TIMESTAMP)
        assertEquals(viewModel.messages.last().type, MessageType.SENT)
        assertTrue(viewModel.messages.last().hasTail)
    }

    @Test
    fun sendMessage_receiveResponse() {
        viewModel.messages.add(Message("Timestamp", MessageType.TIMESTAMP, TWO_SECONDS_AGO))
        viewModel.messages.add(Message("message", MessageType.SENT, TWO_SECONDS_AGO, true))

        viewModel.sendMessage("hello", TEST_TIME)

        assertEquals(viewModel.messages.size, 4)
        assertEquals(viewModel.messages.last().type, MessageType.RECEIVED)
        assertEquals(viewModel.messages.last().message, "hi")
    }
}
package com.patsuyash.messageservice.controller;

import com.patsuyash.messageservice.util.MessageUtil;
import com.patsuyash.messageservice.domain.Message;
import com.patsuyash.messageservice.service.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerTest {

    @Mock
    private MessageService messageService;

    private MessageController messageController;

    @Before
    public void setUp() throws Exception {
        messageController = new MessageController(messageService);
    }

    @Test
    public void shouldCreateMessage() throws Exception {
        final Message savedMessage = stubServiceToReturnStoredMessage();
        final Message message = MessageUtil.createMessage();
        Message returnedMessage = messageController.createMessage(message);
        // verify message was passed to MessageService
        verify(messageService, times(1)).save(message);
        assertEquals("Returned message should come from the service", savedMessage, returnedMessage);
    }

    private Message stubServiceToReturnStoredMessage() {
        final Message message = MessageUtil.createMessage();
        when(messageService.save(any(Message.class))).thenReturn(message);
        return message;
    }


    @Test
    public void shouldListAllMessages() throws Exception {
        stubServiceToReturnExistingMessages(10);
        Collection<Message> messages = messageController.listMessages();
        assertNotNull(messages);
        assertEquals(10, messages.size());
        // verify message was passed to MessageService
        verify(messageService, times(1)).getList();
    }

    private void stubServiceToReturnExistingMessages(int howMany) {
        when(messageService.getList()).thenReturn(MessageUtil.createMessageList(howMany));
    }

}

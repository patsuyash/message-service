package com.patsuyash.messageservice.service;

import com.patsuyash.messageservice.service.exception.MessageAlreadyExistsException;
import com.patsuyash.messageservice.util.MessageUtil;
import com.patsuyash.messageservice.domain.Message;
import com.patsuyash.messageservice.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;

    private MessageService messageService;

    @Before
    public void setUp() throws Exception {
        messageService = new MessageServiceImpl(messageRepository);
    }

    @Test
    public void shouldSaveNewMessage_GivenThereDoesNotExistOneWithTheSameId_ThenTheSavedMessageShouldBeReturned() throws Exception {
        final Message savedMessage = stubRepositoryToReturnMessageOnSave();
        final Message message = MessageUtil.createMessage();
        final Message returnedMessage = messageService.save(message);
        // verify repository was called with message
        verify(messageRepository, times(1)).save(message);
        assertEquals("Returned message should come from the repository", savedMessage, returnedMessage);
    }

    private Message stubRepositoryToReturnMessageOnSave() {
        Message message = MessageUtil.createMessage();
        when(messageRepository.save(any(Message.class))).thenReturn(message);
        return message;
    }

    @Test
    public void shouldSaveNewMessage_GivenThereExistsOneWithTheSameId_ThenTheExceptionShouldBeThrown() throws Exception {
        stubRepositoryToReturnExistingMessage();
        try {
            messageService.save(MessageUtil.createMessage());
            fail("Expected exception");
        } catch (MessageAlreadyExistsException ignored) {
        }
        verify(messageRepository, never()).save(any(Message.class));
    }

    private void stubRepositoryToReturnExistingMessage() {
        final Message message = MessageUtil.createMessage();
        when(messageRepository.findOne(message.getId())).thenReturn(message);
    }

    @Test
    public void shouldListAllMessages_GivenThereExistSome_ThenTheCollectionShouldBeReturned() throws Exception {
        stubRepositoryToReturnExistingMessages(10);
        Collection<Message> list = messageService.getList();
        assertNotNull(list);
        assertEquals(10, list.size());
        verify(messageRepository, times(1)).findAll();
    }

    private void stubRepositoryToReturnExistingMessages(int howMany) {
        when(messageRepository.findAll()).thenReturn(MessageUtil.createMessageList(howMany));
    }

    @Test
    public void shouldListAllMessages_GivenThereNoneExist_ThenTheEmptyCollectionShouldBeReturned() throws Exception {
        stubRepositoryToReturnExistingMessages(0);
        Collection<Message> list = messageService.getList();
        assertNotNull(list);
        assertTrue(list.isEmpty());
        verify(messageRepository, times(1)).findAll();
    }

}

package com.patsuyash.messageservice.service;

import com.patsuyash.messageservice.domain.Message;
import com.patsuyash.messageservice.repository.MessageRepository;
import com.patsuyash.messageservice.service.exception.MessageAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Validated
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final MessageRepository repository;

    @Inject
    public MessageServiceImpl(final MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Message save(@NotNull @Valid final Message message) {
        LOGGER.debug("Creating {}", message);
        Message existing = repository.findOne(message.getId());
        if (existing != null) {
            throw new MessageAlreadyExistsException(
                    String.format("There already exists a message with id=%s", message.getId()));
        }
        return repository.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getList() {
        LOGGER.debug("Retrieving the list of all messages");
        return repository.findAll();
    }

    @Override
    public Message getMessage(String id) {
        return repository.findOne(id);
    }

}

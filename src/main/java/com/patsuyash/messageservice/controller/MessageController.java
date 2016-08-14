package com.patsuyash.messageservice.controller;

import com.patsuyash.messageservice.domain.Message;
import com.patsuyash.messageservice.service.MessageService;
import com.patsuyash.messageservice.service.exception.MessageAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
public class MessageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);
    private final MessageService messageService;

    @Inject
    public MessageController(final MessageService messageService) {
        this.messageService = messageService;
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST,consumes = {"application/json"}, produces = {"application/json"})
    public Message createMessage(@RequestBody @Valid final Message message) {
        LOGGER.debug("Received request to create the {}", message);
        return messageService.save(message);
    }

    @RequestMapping(value = "/message", method = RequestMethod.GET, produces = {"application/json"})
    public List<Message> listMessages() {
        LOGGER.debug("Received request to list all messages");
        return messageService.getList();
    }

    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public   @ResponseBody
    Message getMessage(@PathVariable("id") String id,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {
        Message message = messageService.getMessage(id);
        return message;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleMessageAlreadyExistsException(MessageAlreadyExistsException e) {
        return e.getMessage();
    }

}

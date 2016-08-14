package com.patsuyash.messageservice.service;

import com.patsuyash.messageservice.domain.Message;

import java.util.List;

public interface MessageService {

    Message save(Message message);

    List<Message> getList();

    Message getMessage(String id);
}

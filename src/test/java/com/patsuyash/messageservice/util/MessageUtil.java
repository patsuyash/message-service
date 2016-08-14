package com.patsuyash.messageservice.util;

import com.patsuyash.messageservice.domain.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {

    private static final String ID = "id";
    private static final String NAME = "name";

    private MessageUtil() {
    }

    public static Message createMessage() {
        return new Message(ID, NAME);
    }

    public static List<Message> createMessageList(int howMany) {
        List<Message> messageList = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            messageList.add(new Message(ID + "#" + i, NAME));
        }
        return messageList;
    }

}

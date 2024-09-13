package com.example.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

@Service
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    AccountRepository accountRepository;

    // 3
    public Message postMessage(Message message) {
        Optional<Account> accountOptional = accountRepository.findById(message.getPostedBy());

        if (message.getMessageText().length() > 0 && message.getMessageText().length() <= 255
                && accountOptional.isPresent())
            return messageRepository.save(message);

        return null;
    }

    // 4
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    // 5
    public Message getMessageById(int id) {
        return messageRepository.findById(id).orElse(null);
    }

    // 6
    public int deleteMessageById(int id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (!messageOptional.isPresent())
            return 0;

        messageRepository.deleteById(id);
        return 1;
    }

    // 7
    public int updateMessageById(int id, Message message) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent() && message.getMessageText().length() <= 255
                && message.getMessageText().length() > 0) {
            Message msg = messageOptional.get();
            msg.setMessageText(message.getMessageText());
            messageRepository.save(msg);
            return 1;
        }

        return 0;
    }

    // 8
    public List<Message> getAllMessagesFromUser(int id) {
        return messageRepository.findMessagesByPostedBy(id);
    }
}

package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration.AnnotationConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your
 * controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use
 * the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations.
 * You should
 * refer to prior mini-project labs and lecture materials for guidance on how a
 * controller may be built.
 */
@RestController
public class SocialMediaController {
    @Autowired
    AccountService accountService;
    @Autowired
    MessageService messageService;

    // 1
    @PostMapping("/register")
    public ResponseEntity postNewUserHandler(@RequestBody Account user) {

        Account postedUser = accountService.addNewUser(user);

        if (postedUser.getAccountId() == 409)
            return ResponseEntity.status(409).body(null);
        if (postedUser == null)
            return ResponseEntity.status(400).body(null);

        return ResponseEntity.status(200).body(postedUser);
    }

    // 2
    @PostMapping("/login")
    public ResponseEntity postUserLoginsHandler(@RequestBody Account user) {
        Account loginUser = accountService.userLogin(user);

        if (loginUser == null)
            return ResponseEntity.status(401).body(null);

        return ResponseEntity.status(200).body(loginUser);
    }

    // 3
    @PostMapping("/messages")
    public ResponseEntity postMessagesHandler(@RequestBody Message message) {

        Message postedMessage = messageService.postMessage(message);

        if (postedMessage == null)
            return ResponseEntity.status(400).body("Unauthorized resource!");

        return ResponseEntity.status(200).body(postedMessage);
    }

    // 4
    @GetMapping("/messages")
    public ResponseEntity getAllMessagesHandler() {
        return ResponseEntity.status(200).body(messageService.getAllMessages());
    }

    // 5
    @GetMapping("/messages/{messageId}")
    public ResponseEntity getMessageByIdHandler(@PathVariable("messageId") int id) {
        Message message = messageService.getMessageById(id);

        if (message == null)
            return ResponseEntity.status(200).build();

        return ResponseEntity.status(200).body(message);
    }

    // 6
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMessageByIdHandler(@PathVariable("messageId") int id) {
        int msgDelete = messageService.deleteMessageById(id);

        if (msgDelete == 1)
            return ResponseEntity.status(200).body(1);

        return ResponseEntity.status(200).body(null);
    }

    // 7

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity putUpdateMessageByIdHandler(@PathVariable("messageId") int id, @RequestBody Message message) {

        int msgUpdate = messageService.updateMessageById(id, message);

        if (msgUpdate == 1)
            return ResponseEntity.status(200).body(1);

        return ResponseEntity.status(400).body(null);
    }

    // 8
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity getAllMessagesByUserHandler(@PathVariable("accountId") int id) {
        return ResponseEntity.status(200).body(messageService.getAllMessagesFromUser(id));
    }

}

package com.example.sqsdemo.controller;

import com.example.sqsdemo.dto.MessageRequest;
import com.example.sqsdemo.dto.MessageResponse;
import com.example.sqsdemo.service.SqsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.List;

@RestController
@RequestMapping("/sqs")
@RequiredArgsConstructor
public class SqsController {

    private final SqsService sqsService;

    @PostMapping("/send")
    public ResponseEntity<MessageResponse> sendMessage(@RequestBody MessageRequest request) {
        try {
            String result = sqsService.sendMessage(request.getMessage());
            return ResponseEntity.ok(new MessageResponse(true, result));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new MessageResponse(false, e.getMessage()));
        }
    }

    @GetMapping("/receive")
    public ResponseEntity<List<Message>> receiveMessages() {
        try {
            List<Message> messages = sqsService.receiveMessages();
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}

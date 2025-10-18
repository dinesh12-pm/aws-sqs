package com.example.sqsdemo.controller;

import com.example.sqsdemo.service.SqsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sqs")
@RequiredArgsConstructor
public class SqsController {

    private final SqsService sqsService;

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody String message) {
        String result = sqsService.sendMessage(message);
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/receive")
    public ResponseEntity<List<Message>> receiveMessages() {
        List<Message> messages = sqsService.receiveMessages();
        return ResponseEntity.ok(messages);
    }
}

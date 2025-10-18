package com.example.sqsdemo.controller;

import com.example.sqsdemo.service.SqsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import software.amazon.awssdk.services.sqs.model.Message;
import java.util.List;

@RestController
@RequestMapping("/sqs")
@RequiredArgsConstructor
public class SqsController {

    private final SqsService sqsService;

    @PostMapping("/send")
    public String send(@RequestBody String message) {
        return sqsService.sendMessage(message);
    }

    @GetMapping("/receive")
    public List<Message> receive() {
        return sqsService.receiveMessages();
    }
}

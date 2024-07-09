package com.young.in.youngk.message.telegram.controller;

import com.young.in.youngk.message.telegram.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/sendNotification")
    public String sendNotification(@RequestParam String message, @RequestParam String chatIds) {
        List<String> chatIdList = Arrays.asList(chatIds.split(","));
        notificationService.sendNotification(chatIdList, message);
        return "Notification sent!";
    }

}

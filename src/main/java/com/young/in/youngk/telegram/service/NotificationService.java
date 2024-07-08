package com.young.in.youngk.telegram.service;

import com.young.in.youngk.config.TelegramBotConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationService {

    private final TelegramBotConfig.MyTelegramBot myTelegramBot;
    private final String chatId;


    public NotificationService(TelegramBotConfig.MyTelegramBot myTelegramBot, @Value("${telegram.chat.id}") String chatId) {
        this.myTelegramBot = myTelegramBot;
        this.chatId = chatId;
    }

    public void sendNotification(List<String> chatIds, String message) {
        for (String chatId : chatIds) {
            System.out.println("Sending notification to chatId: " + chatId + " with message: " + message); // 로그 추가
            myTelegramBot.sendNotification(chatId, message);
        }
    }
}

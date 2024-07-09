package com.young.in.youngk.message.telegram.service;

import com.young.in.youngk.config.TelegramBotConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NotificationService {

    private final TelegramBotConfig.MyTelegramBot myTelegramBot;

    public NotificationService(TelegramBotConfig.MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void sendNotification(List<String> chatIds, String message) {
        for (String chatId : chatIds) {
            System.out.println("[Service]Sending notification to chatId: " + chatId + " with message: " + message); // 로그 추가
            myTelegramBot.sendNotification(chatId, message);
        }
    }

}

package com.young.in.youngk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Configuration
public class TelegramBotConfig {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    public MyTelegramBot myTelegramBot() {
        return new MyTelegramBot(botToken, botUsername);
    }

    public static class MyTelegramBot extends TelegramLongPollingBot {

        private final String botToken;
        private final String botUsername;

        public MyTelegramBot(String botToken, String botUsername) {
            this.botToken = botToken;
            this.botUsername = botUsername;
        }

        @Override
        public String getBotToken() {
            return botToken;
        }

        @Override
        public String getBotUsername() {
            return botUsername;
        }

        @Override
        public void onUpdateReceived(Update update) {
            // 봇이 수신한 메시지에 대한 처리 로직
            if (update.hasMessage() && update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                long chatId = update.getMessage().getChatId();

                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(chatId));  // chatId를 문자열로 변환하여 설정
                message.setText("Received your message: " + messageText);

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }


        public void sendNotification(String chatId, String message) {
            System.out.println("Sending message to chatId: " + chatId + " with message: " + message); // 로그 추가
            String urlString = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s", botToken, chatId, message);
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                int responseCode = conn.getResponseCode();
                System.out.println("Response Code: " + responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    System.out.println("Response: " + response.toString());
                } else {
                    System.out.println("GET request not worked");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

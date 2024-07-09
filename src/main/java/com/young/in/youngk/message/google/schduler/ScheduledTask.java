package com.young.in.youngk.message.google.schduler;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.young.in.youngk.message.google.service.GoogleCalendarService;
import com.young.in.youngk.message.telegram.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    private GoogleCalendarService googleCalendarService;

    @Autowired
    private NotificationService notificationService;

    public static String convertDateTime(DateTime googleDateTime) {
        if (googleDateTime == null) return "";
        // DateTime을 ZonedDateTime으로 변환
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(googleDateTime.toStringRfc3339());

        // 날짜-시간-요일 형식의 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm EEEE");

        // 포맷팅하여 문자열로 변환
        return zonedDateTime.format(formatter);
    }


    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void checkForUpdates() throws IOException {
        List<Event> events = googleCalendarService.getEvents();
        Instant now = Instant.now();
        Instant oneMinuteAgo = now.minusSeconds(60);

        for (Event event : events) {
            DateTime createdDateTime = event.getCreated();
            if (createdDateTime != null) {
                Instant eventCreatedTime = Instant.ofEpochMilli(createdDateTime.getValue());
                if (eventCreatedTime.isAfter(oneMinuteAgo) && eventCreatedTime.isBefore(now)) {
                    String startDate = convertDateTime(event.getStart().getDateTime());
                    String endDate = convertDateTime(event.getEnd().getDateTime());
                    List<String> chatIdList = Arrays.asList("1492434707,2140097561".split(","));
                    String message = "[Google 일정]" +  event.getSummary()
                             + "\n 시작일 : " + startDate
                             + "\n 종료일 : " + endDate ;
                    notificationService.sendNotification(chatIdList, message);
                    System.out.println("New Event: " + message);
                }
            }
        }
    }


}

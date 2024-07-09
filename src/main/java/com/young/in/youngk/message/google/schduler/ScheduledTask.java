package com.young.in.youngk.message.google.schduler;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.young.in.youngk.message.google.service.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ScheduledTask {

    @Autowired
    private GoogleCalendarService googleCalendarService;

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

        // 날짜-시간-요일 형식의 포맷터
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm EEEE");

        for (Event event : events) {

            DateTime startDateTime = event.getStart().getDateTime();
            DateTime endDateTime = event.getEnd().getDateTime();


            String formattedDate1 = convertDateTime(startDateTime);
            String formattedDate2 = convertDateTime(endDateTime);

            System.out.println("Event: " + event.getSummary() +
                    " Start: " + formattedDate1 +
                    " End: " + formattedDate2
            );
        }
    }


}

package com.young.in.youngk.message.google.service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Channel;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class GoogleCalendarService {

    @Autowired
    private Calendar calendarService;

    public List<Event> getEvents() throws IOException {
        // 현재 시간 설정
        DateTime now = new DateTime(System.currentTimeMillis());

        // 현재 시간 이후의 이벤트를 가져오기 위해 timeMin 파라미터 사용
        Events events = calendarService.events().list("primary")
                .setMaxResults(10)  // 가져올 최대 이벤트 수
                .setTimeMin(now)  // 현재 시간 이후의 이벤트만 조회
                .setOrderBy("startTime")  // 시작 시간 기준으로 정렬
                .setSingleEvents(true)  // 반복 이벤트를 단일 이벤트로 확장
                .execute();
        return events.getItems();
    }
}

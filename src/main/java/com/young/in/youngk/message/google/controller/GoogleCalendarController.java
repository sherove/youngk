package com.young.in.youngk.message.google.controller;

import com.google.api.services.calendar.model.Event;
import com.young.in.youngk.message.google.service.GoogleCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
@RequestMapping("/calendar")
public class GoogleCalendarController {

    @Autowired
    private GoogleCalendarService googleCalendarService;



}

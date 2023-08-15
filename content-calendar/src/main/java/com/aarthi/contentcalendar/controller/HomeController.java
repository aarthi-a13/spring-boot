package com.aarthi.contentcalendar.controller;

import com.aarthi.contentcalendar.config.ContentCalendarProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final ContentCalendarProperties properties;

    public HomeController(ContentCalendarProperties properties) {
        this.properties = properties;
    }

    //To provide custom properties
    @GetMapping("/")
    public ContentCalendarProperties home() {
        return properties;
    }
}

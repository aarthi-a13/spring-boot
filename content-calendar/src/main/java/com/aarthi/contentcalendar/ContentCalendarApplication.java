package com.aarthi.contentcalendar;

import com.aarthi.contentcalendar.config.ContentCalendarProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ContentCalendarProperties.class)
public class ContentCalendarApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentCalendarApplication.class, args);
    }


}
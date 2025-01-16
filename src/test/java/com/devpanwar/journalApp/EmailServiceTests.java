package com.devpanwar.journalApp;

import com.devpanwar.journalApp.scheduler.UserScheduler;
import com.devpanwar.journalApp.service.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Disabled
    @Test
    void sendEmail(){
        emailService.sendEmail("devpanwar2907@gmail.com","Texting SMTP java", "Hello Dev!, We are testing Java mail sender");
    }
}

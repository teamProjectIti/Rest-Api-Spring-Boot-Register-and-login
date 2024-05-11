package com.spacek.service.Implement.Notification;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    public void send(String phoneNumber, String message) {
        System.out.println("Sending SMS to " + phoneNumber + ": " + message);
    }
}

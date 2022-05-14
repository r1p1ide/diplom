package org.example.diplom.service;

public interface SmsSenderService {

    void sendSms(String phone, String text, String sender);
}

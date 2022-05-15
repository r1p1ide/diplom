package org.example.diplom.service;

public interface ChangePasswordService {

    void resetPassword(String login);

    void changePassword(String login, String oldPassword, String newPassword, String repeatPassword);
}

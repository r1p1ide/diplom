package org.example.diplom.service;

import org.example.diplom.dto.DeleteUserDto;
import org.example.diplom.dto.UserDto;

public interface AdministrationService {

    void addUser(UserDto userDto);

    void deleteUser(DeleteUserDto deleteUserDto);
}

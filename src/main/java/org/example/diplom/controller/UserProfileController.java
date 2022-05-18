package org.example.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.example.diplom.exception.ApiInvalidParametersException;
import org.example.diplom.exception.ApiUserFoundException;
import org.example.diplom.exception.ApiUserNotFoundException;
import org.example.diplom.model.UserInfo;
import org.example.diplom.repository.UserInfoRepository;
import org.example.diplom.service.impl.AdministrationServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.*;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class UserProfileController {

    private final Logger LOG = Logger.getLogger(AdministrationServiceImpl.class.getCanonicalName());

    private final UserInfoRepository userInfoRepository;

    @GetMapping("/me")
    private List<UserInfo> findMe(@RequestParam("login") String login) {
        LOG.log(Level.INFO, ENTRY);
        List<UserInfo> result = userInfoRepository.findByLogin(login);
        if (result.isEmpty()) {
            LOG.log(Level.INFO, THROW);
            throw new ApiUserNotFoundException("Не существует пользователя с таким логином.");
        }
        LOG.log(Level.INFO, EXIT);
        return result;
    }

    @GetMapping("/all")
    private List<UserInfo> findAll() {
        LOG.log(Level.INFO, ENTRY);
        List<UserInfo> result = userInfoRepository.findAll();
        if (result.isEmpty()) {
            LOG.log(Level.INFO, THROW);
            throw new ApiUserNotFoundException("Не существует пользователя с таким логином.");
        }
        LOG.log(Level.INFO, EXIT);
        return result;
    }

    @GetMapping("/logout")
    public String logout() {
        return "Вы успешно вышли из системы.";
    }
}

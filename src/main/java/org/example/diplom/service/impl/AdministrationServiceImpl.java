package org.example.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.diplom.dao.AuthInfoRepository;
import org.example.diplom.dao.UserInfoRepository;
import org.example.diplom.dto.UserDto;
import org.example.diplom.exception.ApiException;
import org.example.diplom.exception.ApiInvalidParametersException;
import org.example.diplom.model.AuthInfo;
import org.example.diplom.model.UserInfo;
import org.example.diplom.service.AdministrationService;
import org.example.diplom.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.ENTRY;
import static org.example.diplom.resources.LoggerResources.EXIT;
import static org.example.diplom.util.GenerateRandomPassword.generateRandomPassword;
import static org.example.diplom.util.GenerateRandomPassword.toMD5;

@Service
@RequiredArgsConstructor
public class AdministrationServiceImpl implements AdministrationService {

    private final static Logger LOG = Logger.getLogger(AdministrationServiceImpl.class.getCanonicalName());

    private final UserInfoRepository userInformationRepository;

    private final AuthInfoRepository authInformationRepository;

    @Autowired
    private EmailSenderService senderService;

    @SneakyThrows
    @Override
    public void addUser(UserDto userDto) {

        LOG.log(Level.INFO, ENTRY);

        if (!(userDto.getLogin().isEmpty() &&
                userDto.getFirstName().isEmpty() &&
                userDto.getLastName().isEmpty() &&
                userDto.getEmail().isEmpty() &&
                userDto.getPhone().isEmpty()) &&
                (userDto.getRole().equalsIgnoreCase("Admin") || userDto.getRole().equalsIgnoreCase("User"))) {

            AuthInfo auth = new AuthInfo();
            auth.setLogin(userDto.getLogin());
            auth.setPassword(generateRandomPassword());
            senderService.sendEmail(userDto.getEmail(),
                    "Вы были успешно зарегистрированы.",
                    "Ваш логин: " + userDto.getLogin() + "\nВаш пароль: " + auth.getPassword());
            auth.setPassword(toMD5(auth.getPassword()));
            try {
                authInformationRepository.save(auth);
                UserInfo user = new UserInfo(userDto);
                user.setAuth_id(auth.getId());
                userInformationRepository.save(user);

            } catch (ApiException e) {
                throw new ApiInvalidParametersException("Request parameters are missing or not in the correct format.");
            }

            LOG.log(Level.INFO, EXIT);
        }
    }

    @Override
    public void deleteUser(String login) {

        LOG.log(Level.INFO, ENTRY);

        if (!(login.isEmpty())) {

        }
    }

}

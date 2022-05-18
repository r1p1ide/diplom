package org.example.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.diplom.dto.DeleteUserDto;
import org.example.diplom.exception.ApiUserNotFoundException;
import org.example.diplom.repository.AuthInfoRepository;
import org.example.diplom.repository.UserInfoRepository;
import org.example.diplom.dto.UserDto;
import org.example.diplom.exception.ApiInvalidParametersException;
import org.example.diplom.exception.ApiUserFoundException;
import org.example.diplom.model.AuthInfo;
import org.example.diplom.model.UserInfo;
import org.example.diplom.service.AdministrationService;
import org.example.diplom.service.EmailSenderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.*;
import static org.example.diplom.util.GenerateRandomPassword.generateRandomPassword;
import static org.example.diplom.util.GenerateRandomPassword.toMD5;
import static org.example.diplom.util.RegexResources.*;

@Service
@RequiredArgsConstructor
public class AdministrationServiceImpl implements AdministrationService {

    private final Logger LOG = Logger.getLogger(AdministrationServiceImpl.class.getCanonicalName());

    private final UserInfoRepository userInformationRepository;

    private final AuthInfoRepository authInformationRepository;

    private final EmailSenderService senderService;

    @SneakyThrows
    @Override
    public void addUser(UserDto userDto) {

        LOG.log(Level.INFO, ENTRY);

        if (
                userDto.getLogin().matches(LOGIN_CONDITION) && !userDto.getLogin().isEmpty() &&
                userDto.getFirstName().matches(NAME_CONDITION) && !userDto.getFirstName().isEmpty() &&
                userDto.getLastName().matches(NAME_CONDITION) && !userDto.getLastName().isEmpty() &&
                userDto.getEmail().matches(EMAIL_CONDITION) &&
                userDto.getPhone().matches(PHONE_CONDITION) &&
                (userDto.getRole().equals("admin")
                        || userDto.getRole().equals("user"))) {

            List<UserInfo> userList = userInformationRepository.findByLogin(userDto.getLogin());
            if (userList.isEmpty()) {
                AuthInfo auth = new AuthInfo();
                auth.setLogin(userDto.getLogin());
                auth.setPassword(generateRandomPassword());
                senderService.sendEmail(userDto.getEmail(),
                        "Вы были успешно зарегистрированы.",
                        "Ваш логин: " + userDto.getLogin() + "\nВаш пароль: " + auth.getPassword());
                auth.setPassword(toMD5(auth.getPassword()));

                authInformationRepository.save(auth);
                UserInfo user = new UserInfo(userDto);
                user.setAuth_id(auth.getId());
                userInformationRepository.save(user);
            } else {
                LOG.log(Level.INFO, THROW);
                throw new ApiUserFoundException("Пользователь с таким логином уже существует!");
            }

            LOG.log(Level.INFO, EXIT);

        } else {
            LOG.log(Level.INFO, THROW);
            throw new ApiInvalidParametersException(
                    "Неободимые параметры для запроса отсутствуют или имеют неверный формат.");
        }
    }

    @Override
    public void deleteUser(DeleteUserDto deleteUserDto) {

        LOG.log(Level.INFO, ENTRY);

        List<UserInfo> userList = userInformationRepository.findByLogin(deleteUserDto.getLogin());
        List<AuthInfo> authList = authInformationRepository.findByLogin(deleteUserDto.getLogin());
        if (userList.isEmpty() ) {
            LOG.log(Level.INFO, THROW);
            throw new ApiUserNotFoundException("Не существует пользователя с таким логином.");
        }
        UserInfo userInfo = userList.get(0);
        AuthInfo authInfo = authList.get(0);
        if (userInfo.getRole().equalsIgnoreCase("admin")) {
            LOG.log(Level.INFO, THROW);
            throw new ApiInvalidParametersException("Нельзя удалить админа.");
        }
        else {
            userInformationRepository.delete(userInfo);
            authInformationRepository.delete(authInfo);
        }
    }
}

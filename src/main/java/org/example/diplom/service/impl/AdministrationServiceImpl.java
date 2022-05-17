package org.example.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.diplom.dao.AuthInfoRepository;
import org.example.diplom.dao.UserInfoRepository;
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

import static org.example.diplom.resources.LoggerResources.ENTRY;
import static org.example.diplom.resources.LoggerResources.EXIT;
import static org.example.diplom.util.GenerateRandomPassword.generateRandomPassword;
import static org.example.diplom.util.GenerateRandomPassword.toMD5;
import static org.example.diplom.util.RegexResources.*;

@Service
@RequiredArgsConstructor
public class AdministrationServiceImpl implements AdministrationService {

    private final static Logger LOG = Logger.getLogger(AdministrationServiceImpl.class.getCanonicalName());

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
                throw new ApiUserFoundException("Пользователь с таким логином уже существует!");
            }

            LOG.log(Level.INFO, EXIT);

        } else {
            throw new ApiInvalidParametersException(
                    "Неободимые параметры для запроса отсутствуют или имеют неверный формат.");
        }
    }
}

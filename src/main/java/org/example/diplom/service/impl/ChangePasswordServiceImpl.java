package org.example.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.diplom.repository.AuthInfoRepository;
import org.example.diplom.repository.UserInfoRepository;
import org.example.diplom.exception.ApiException;
import org.example.diplom.exception.ApiInvalidParametersException;
import org.example.diplom.model.AuthInfo;
import org.example.diplom.model.UserInfo;
import org.example.diplom.service.ChangePasswordService;
import org.example.diplom.service.EmailSenderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.ENTRY;
import static org.example.diplom.resources.LoggerResources.THROW;
import static org.example.diplom.util.GenerateRandomPassword.generateRandomPassword;
import static org.example.diplom.util.GenerateRandomPassword.toMD5;

@Service
@RequiredArgsConstructor
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final static Logger LOG = Logger.getLogger(AdministrationServiceImpl.class.getCanonicalName());

    private final UserInfoRepository userInformationRepository;

    private final AuthInfoRepository authInformationRepository;

    private final EmailSenderService senderService;

    @Override
    public void resetPassword(String login) {
        LOG.log(Level.INFO, ENTRY);

        try {
            List<AuthInfo> authData = authInformationRepository.findByLogin(login);
            List<UserInfo> userData = userInformationRepository.findByLogin(login);

            LOG.log(Level.INFO, authData.toString());

            AuthInfo auth = authData.get(0);
            UserInfo user = userData.get(0);

            auth.setPassword(generateRandomPassword());
            senderService.sendEmail(user.getEmail(),
                    "Вы успешно сменили пароль.",
                    "Ваш логин: " + login + "\nВаш новый пароль: " + auth.getPassword());
            auth.setPassword(toMD5(auth.getPassword()));
            authInformationRepository.save(auth);

        } catch (ApiException e) {
            throw new ApiInvalidParametersException(
                    "Неободимые параметры для запроса отсутствуют или имеют неверный формат.");
        }
    }

    @Override
    public void changePassword(String login, String oldPassword, String newPassword, String repeatPassword) {
        LOG.log(Level.INFO, ENTRY);

        try {
            List<AuthInfo> authData = authInformationRepository.findByLogin(login);
            List<UserInfo> userData = userInformationRepository.findByLogin(login);

            LOG.log(Level.INFO, authData.toString());

            AuthInfo auth = authData.get(0);
            UserInfo user = userData.get(0);
            
            if (DigestUtils.md5Hex(oldPassword).equals(auth.getPassword()) &&
                    newPassword.equals(repeatPassword) &&
                    !(oldPassword.equals(newPassword))) {
                senderService.sendEmail(user.getEmail(),
                        "Вы успешно обновили пароль.",
                        "Если вы видите данное сообщение, то вы успешно обновили пароль.");
                auth.setPassword(DigestUtils.md5Hex(newPassword));
                authInformationRepository.save(auth);
            } else {
                LOG.log(Level.INFO, THROW);
                throw new ApiInvalidParametersException(
                        "Неободимые параметры для запроса отсутствуют или имеют неверный формат.");
            }
        } catch (ApiException e) {
            LOG.log(Level.INFO, THROW);
            throw new ApiInvalidParametersException(
                    "Неободимые параметры для запроса отсутствуют или имеют неверный формат.");
        }
    }
}

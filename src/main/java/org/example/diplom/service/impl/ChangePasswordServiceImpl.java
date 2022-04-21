package org.example.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.diplom.dao.AuthInfoRepository;
import org.example.diplom.dao.UserInfoRepository;
import org.example.diplom.exception.ApiException;
import org.example.diplom.exception.ApiInvalidParametersException;
import org.example.diplom.model.AuthInfo;
import org.example.diplom.model.UserInfo;
import org.example.diplom.service.ChangePasswordService;
import org.example.diplom.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.ENTRY;
import static org.example.diplom.util.GenerateRandomPassword.generateRandomPassword;
import static org.example.diplom.util.GenerateRandomPassword.toMD5;

@Service
@RequiredArgsConstructor
public class ChangePasswordServiceImpl implements ChangePasswordService {

    private final static Logger LOG = Logger.getLogger(AdministrationServiceImpl.class.getCanonicalName());

    private final UserInfoRepository userInformationRepository;

    private final AuthInfoRepository authInformationRepository;

    @Autowired
    private EmailSenderService senderService;

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
            throw new ApiInvalidParametersException("Request parameters are missing or not in the correct format.");
        }
    }
}

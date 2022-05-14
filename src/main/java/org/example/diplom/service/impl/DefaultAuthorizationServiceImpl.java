package org.example.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.diplom.dao.AuthInfoRepository;
import org.example.diplom.dao.UserInfoRepository;
import org.example.diplom.dto.AuthDto;
import org.example.diplom.dto.AuthWithoutCodeDto;
import org.example.diplom.exception.ApiException;
import org.example.diplom.exception.ApiInvalidParametersException;
import org.example.diplom.model.AuthInfo;
import org.example.diplom.model.UserInfo;
import org.example.diplom.service.AuthorizationService;
import org.example.diplom.service.EmailSenderService;
import org.example.diplom.service.SmsSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.*;
import static org.example.diplom.util.GenerateRandomCode.generateRandomCode;

@Service
@RequiredArgsConstructor
public class DefaultAuthorizationServiceImpl implements AuthorizationService {

    private final static Logger LOG = Logger.getLogger(AdministrationServiceImpl.class.getCanonicalName());

    private final AuthInfoRepository authInfoRepository;

    private final UserInfoRepository userInfoRepository;

    private final EmailSenderService senderService;

    private final SmsSenderService smsSenderService;

    @Override
    public Boolean signIn(AuthWithoutCodeDto authWithoutCodeDto) {
        LOG.log(Level.INFO, ENTRY);

        try {
            List<AuthInfo> authList =
                    authInfoRepository.findByLogin(authWithoutCodeDto.getLogin());

            List<UserInfo> userList =
                    userInfoRepository.findByLogin(authWithoutCodeDto.getLogin());

            AuthInfo authInfo = authList.get(0);

            UserInfo userInfo = userList.get(0);

            if (DigestUtils.md5Hex(authWithoutCodeDto.getPassword()).equals(authInfo.getPassword())) {
                authInfo.setCode(generateRandomCode());
                authInfoRepository.save(authInfo);
                senderService.sendEmail(userInfo.getEmail(),
                        "Одноразовый проверочный код",
                        "Никому не сообщайте этот код: " + authInfo.getCode() +
                        "\nПожалуйста, перейдите по этой ссылке и введите данные: " +
                        "http://localhost:8080/auth/sign-in/verify");
                LOG.log(Level.INFO, EXIT);
                return true;
            } else {
                LOG.log(Level.INFO, EXIT);
                return false;
            }
        } catch (ApiException e) {
            throw new ApiInvalidParametersException(
                    "Неободимые параметры для запроса отсутствуют или имеют неверный формат.");
        }
    }

    @Override
    public Boolean signInV2(AuthWithoutCodeDto authWithoutCodeDto) {
        LOG.log(Level.INFO, ENTRY);

        try {
            List<AuthInfo> authList =
                    authInfoRepository.findByLogin(authWithoutCodeDto.getLogin());

            AuthInfo authInfo = authList.get(0);

            if (DigestUtils.md5Hex(authWithoutCodeDto.getPassword()).equals(authInfo.getPassword())) {
                authInfo.setCode(generateRandomCode());
                authInfoRepository.save(authInfo);
                smsSenderService.sendSms("79160899444",
                        "Никому не сообщайте этот код: " + authInfo.getCode(),
                        "TEST-SMS");
                LOG.log(Level.INFO, EXIT);
                return true;
            } else {
                LOG.log(Level.INFO, THROW);
                return false;
            }
        } catch (ApiException e) {
            throw new ApiInvalidParametersException(
                    "Неободимые параметры для запроса отсутствуют или имеют неверный формат.");
        }
    }

    @Override
    public Boolean signInWithCode(AuthDto authDto) {
        LOG.log(Level.INFO, ENTRY);

        try {
            List<AuthInfo> authList =
                    authInfoRepository.findByLogin(authDto.getLogin());

            AuthInfo authInfo = authList.get(0);

            if (DigestUtils.md5Hex(authDto.getPassword()).equals(authInfo.getPassword()) &&
            authDto.getCode().equals(authInfo.getCode())) {
                LOG.log(Level.INFO, EXIT);
                return true;
            } else {
                LOG.log(Level.INFO, EXIT);
                throw new ApiInvalidParametersException("Неверный код.");
            }
        } catch (ApiException e) {
            throw new ApiInvalidParametersException(
                    "Неободимые параметры для запроса отсутствуют или имеют неверный формат.");
        }
    }
}

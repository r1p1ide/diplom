package org.example.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.diplom.dao.AuthInfoRepository;
import org.example.diplom.dao.UserInfoRepository;
import org.example.diplom.dto.AuthDto;
import org.example.diplom.exception.ApiException;
import org.example.diplom.exception.ApiInvalidParametersException;
import org.example.diplom.model.AuthInfo;
import org.example.diplom.model.UserInfo;
import org.example.diplom.service.AuthorizationService;
import org.example.diplom.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.ENTRY;
import static org.example.diplom.resources.LoggerResources.EXIT;
import static org.example.diplom.util.GenerateCode.generateCode;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final static Logger LOG = Logger.getLogger(AdministrationServiceImpl.class.getCanonicalName());

    private final AuthInfoRepository authInformationRepository;

    private final UserInfoRepository userInfoRepository;

    @Autowired
    private EmailSenderService senderService;

    @Override
    public Boolean signIn(AuthDto dto) {
        LOG.log(Level.INFO, ENTRY);

        try {
            List<AuthInfo> authList =
                    authInformationRepository.findByLogin(dto.getLogin());

            AuthInfo authInfo = authList.get(0);
            authInfo.setCode(generateCode());

          if (!(authInfo.getCode().equals(dto.getCode())) &&
                    DigestUtils.md5Hex(dto.getPassword()).equals(authInfo.getPassword())) {
                List<UserInfo> userList = userInfoRepository.findByLogin(dto.getLogin());
                UserInfo userInfo = userList.get(0);

                senderService.sendEmail(userInfo.getEmail(),
                        "Ваш проверочный код:",
                        "Чтобы войти, используйте этот проверочный код: " + authInfo.getCode());

                authInformationRepository.save(authInfo);

                } else {
                    LOG.log(Level.INFO, EXIT);
                    return false;
                }
        } catch (ApiException e) {
            throw new ApiInvalidParametersException("Request parameters are missing or not in the correct format.");
        }
        return null;
    }
}

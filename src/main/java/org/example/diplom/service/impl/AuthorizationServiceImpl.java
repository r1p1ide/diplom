package org.example.diplom.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.diplom.dao.AuthInfoRepository;
import org.example.diplom.dto.AuthDto;
import org.example.diplom.exception.ApiException;
import org.example.diplom.exception.ApiInvalidParametersException;
import org.example.diplom.model.AuthInfo;
import org.example.diplom.service.AuthorizationService;
import org.example.diplom.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.ENTRY;
import static org.example.diplom.resources.LoggerResources.EXIT;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final static Logger LOG = Logger.getLogger(AdministrationServiceImpl.class.getCanonicalName());

    private final AuthInfoRepository authInformationRepository;

    @Autowired
    private EmailSenderService senderService;

    @Override
    public Boolean signIn(AuthDto dto) {
        LOG.log(Level.INFO, ENTRY);

        try {
            List<AuthInfo> userOptional =
                    authInformationRepository.findByLogin(dto.getLogin());

            AuthInfo authInfo = userOptional.get(0);

            if (DigestUtils.md5Hex(dto.getPassword()).equals(authInfo.getPassword())) {
                    LOG.log(Level.INFO, EXIT);
                    return true;
                } else {
                    LOG.log(Level.INFO, EXIT);
                    return false;
                }
        } catch (ApiException e) {
            throw new ApiInvalidParametersException("Request parameters are missing or not in the correct format.");
        }
    }

}

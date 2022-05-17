package org.example.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.example.diplom.dao.AuthInfoRepository;
import org.example.diplom.dao.TokenRepository;
import org.example.diplom.dto.AuthDto;
import org.example.diplom.dto.AuthResponse;
import org.example.diplom.dto.AuthWithoutCodeDto;
import org.example.diplom.model.AuthInfo;
import org.example.diplom.model.Token;
import org.example.diplom.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.ENTRY;
import static org.example.diplom.resources.LoggerResources.EXIT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.example.diplom.util.GenerateToken.generateToken;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final static Logger LOG = Logger.getLogger(AdminPageController.class.getCanonicalName());

    private final AuthorizationService authorizationService;

    private final AuthInfoRepository authInfoRepository;

    private final TokenRepository tokenRepository;

    @PostMapping(value = "/sign-in", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody AuthWithoutCodeDto authWithoutCodeDto){
        LOG.log(Level.INFO, ENTRY);

        authorizationService.signIn(authWithoutCodeDto);

        List<AuthInfo> authInfoList = authInfoRepository.findByLogin(authWithoutCodeDto.getLogin());
        AuthInfo authInfo = authInfoList.get(0);
        List<Token> tokenList = tokenRepository.findByAuthid(authInfo.getId());
        Token token = tokenList.get(0);

        token.setToken(Base64.getEncoder().encodeToString(generateToken(token.getAuthid()).getBytes()));
        tokenRepository.save(token);

        LOG.log(Level.INFO, EXIT);
        return ResponseEntity.status(HttpStatus.OK).body(
                new AuthResponse(true, LocalDateTime.now(), "OK"));
    }

    @PostMapping(value = "/sign-in-v2", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> signInV2(@Valid @RequestBody AuthWithoutCodeDto authWithoutCodeDto){
        LOG.log(Level.INFO, ENTRY);

        authorizationService.signInV2(authWithoutCodeDto);

        List<AuthInfo> authInfoList = authInfoRepository.findByLogin(authWithoutCodeDto.getLogin());
        AuthInfo authInfo = authInfoList.get(0);
        List<Token> tokenList = tokenRepository.findByAuthid(authInfo.getId());
        Token token = tokenList.get(0);

        token.setToken(Base64.getEncoder().encodeToString(generateToken(token.getAuthid()).getBytes()));
        tokenRepository.save(token);

        LOG.log(Level.INFO, EXIT);
        return ResponseEntity.status(HttpStatus.OK).body(
                new AuthResponse(true, LocalDateTime.now(), "OK"));
    }

    @PostMapping(value = "/sign-in/verify", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> signInWithCode(@Valid @RequestBody AuthDto authDto){
        LOG.log(Level.INFO, ENTRY);

        authorizationService.signInWithCode(authDto);

        LOG.log(Level.INFO, EXIT);
        return ResponseEntity.status(HttpStatus.OK).body(
                new AuthResponse(true, LocalDateTime.now(), "OK"));
    }
}

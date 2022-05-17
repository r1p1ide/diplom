package org.example.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.example.diplom.dto.AuthDto;
import org.example.diplom.dto.AuthResponse;
import org.example.diplom.dto.AuthWithoutCodeDto;
import org.example.diplom.service.AuthorizationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.example.diplom.resources.LoggerResources.ENTRY;
import static org.example.diplom.resources.LoggerResources.EXIT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final static Logger LOG = Logger.getLogger(AdminPageController.class.getCanonicalName());

    private final AuthorizationService authorizationService;

    @PostMapping(value = "/sign-in", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody AuthWithoutCodeDto authWithoutCodeDto){
        LOG.log(Level.INFO, ENTRY);

        authorizationService.signIn(authWithoutCodeDto);

        LOG.log(Level.INFO, EXIT);
        return ResponseEntity.status(HttpStatus.OK).body(
                new AuthResponse(true, LocalDateTime.now(), "OK"));
    }

    @PostMapping(value = "/sign-in-v2", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> signInV2(@Valid @RequestBody AuthWithoutCodeDto authWithoutCodeDto){
        LOG.log(Level.INFO, ENTRY);

        authorizationService.signInV2(authWithoutCodeDto);

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

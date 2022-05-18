package org.example.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.example.diplom.dto.*;
import org.example.diplom.dto.response.ChangePasswordResponse;
import org.example.diplom.service.ChangePasswordService;
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
@RequestMapping("/change")
@RequiredArgsConstructor
public class ChangePasswordController {

    private final static Logger LOG = Logger.getLogger(AdminPageController.class.getCanonicalName());

    private final ChangePasswordService changePasswordService;

    @PostMapping(value = "/reset-password", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ChangePasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordDto dto){

        LOG.log(Level.INFO, ENTRY);

        changePasswordService.resetPassword(dto.getLogin());

        LOG.log(Level.INFO, EXIT);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ChangePasswordResponse(true, LocalDateTime.now(), "OK"));
    }

    @PostMapping(value = "/password", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<ChangePasswordResponse> changePassword(@Valid @RequestBody ChangePasswordDto dto){

        LOG.log(Level.INFO, ENTRY);

        changePasswordService.changePassword(dto.getLogin(), dto.getOldPassword(), dto.getNewPassword(), dto.getRepeatPassword());

        LOG.log(Level.INFO, EXIT);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ChangePasswordResponse(true, LocalDateTime.now(), "OK"));
    }
}

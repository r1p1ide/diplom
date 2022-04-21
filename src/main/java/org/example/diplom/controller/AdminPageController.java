package org.example.diplom.controller;

import lombok.RequiredArgsConstructor;
import org.example.diplom.dto.UserDto;
import org.example.diplom.dto.UserResponse;
import org.example.diplom.service.AdministrationService;
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
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminPageController {

    private final static Logger LOG = Logger.getLogger(AdminPageController.class.getCanonicalName());

    private final AdministrationService administrationService;

    @PostMapping(value = "/addUser", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserDto userDto){

        LOG.log(Level.INFO, ENTRY);

        administrationService.addUser(userDto);

        LOG.log(Level.INFO, EXIT);
        return ResponseEntity.status(HttpStatus.OK).body(
                new UserResponse(true, LocalDateTime.now(), "OK"));
    }
}

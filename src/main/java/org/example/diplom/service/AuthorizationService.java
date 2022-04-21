package org.example.diplom.service;

import org.example.diplom.dto.AuthDto;

public interface AuthorizationService {

    Boolean signIn(AuthDto dto);
}

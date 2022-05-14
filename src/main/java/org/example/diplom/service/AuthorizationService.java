package org.example.diplom.service;

import org.example.diplom.dto.AuthDto;
import org.example.diplom.dto.AuthWithoutCodeDto;

public interface AuthorizationService {

    Boolean signIn(AuthWithoutCodeDto authWithoutCodeDto);

    Boolean signInWithCode(AuthDto authDto);
}

package org.example.diplom.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChangePasswordResponse extends Response {

    public ChangePasswordResponse(Boolean success, LocalDateTime dateTime, String message) {
        super.setSuccess(success);
        super.setTimestamp(dateTime);
        super.setMessage(message);
    }
}

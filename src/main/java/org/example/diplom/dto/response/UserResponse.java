package org.example.diplom.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse extends Response {

    public UserResponse(Boolean success, LocalDateTime dateTime, String message) {
        super.setSuccess(success);
        super.setTimestamp(dateTime);
        super.setMessage(message);
    }
}

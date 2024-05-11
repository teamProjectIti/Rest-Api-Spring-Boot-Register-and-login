package com.spacek.Payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String email;

    public RegisterRequest(String userName, String password, String phone, String email) {
        userName = userName;
        password = password;
        phone = phone;
        email = email;
    }
}

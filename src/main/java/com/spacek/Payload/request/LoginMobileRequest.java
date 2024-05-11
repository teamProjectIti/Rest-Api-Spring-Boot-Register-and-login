package com.spacek.Payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginMobileRequest {
    @NotEmpty
    private String Phone;

    @NotEmpty
    private String UserName;
}

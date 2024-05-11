package com.spacek.Payload.dto;

import com.spacek.entity.security.Otp;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class OtpResponse {
    private String error;
    private Otp otp;

    public boolean hasError() {
        return this.error != null;
    }
}


package com.spacek.service.Interface;

import com.spacek.Payload.Security.JWTAuthResponse;
import com.spacek.Payload.request.LoginRequest;
import com.spacek.Payload.request.LoginMobileRequest;
import com.spacek.Payload.request.RegisterRequest;
import com.spacek.Payload.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    BaseResponse<RegisterRequest> CreateNewUser(RegisterRequest model);
    BaseResponse<String> RegisterClient();
    BaseResponse<JWTAuthResponse> LoginWorkSpace(LoginRequest model);

    BaseResponse<String> CreateNewClient(LoginMobileRequest model);

    ResponseEntity<JWTAuthResponse> mobileLogin(String userName, String phone);
}

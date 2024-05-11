package com.spacek.controller.user;
import com.spacek.Payload.Security.JWTAuthResponse;
import com.spacek.Payload.request.LoginMobileRequest;
import com.spacek.Payload.request.LoginRequest;
import com.spacek.Payload.request.RegisterRequest;
import com.spacek.Payload.response.BaseResponse;
import com.spacek.service.Interface.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")

public class AuthenticationController {

    @Autowired
    private  IUserService _userService;

    @PostMapping("RegisterWorkSpace")
    public BaseResponse<RegisterRequest> RegisterWorkSpace(@Valid @RequestBody RegisterRequest model) {
        return _userService.CreateNewUser(model);
    }
    @PostMapping("RegisterMobile")
    public BaseResponse<String> RegisterMobile(@Valid @RequestBody LoginMobileRequest model) {
        return _userService.CreateNewClient(model);
    }
    @PostMapping("/User/{UserName}/Mobile/{Phone}")
    public ResponseEntity<JWTAuthResponse> UpdateComment(
            @PathVariable(value = "UserName") String UserName,
            @PathVariable(value = "Phone") String Phone
            )
    {
        return _userService.mobileLogin(UserName,Phone);
    }
    
    @PostMapping(value ={ "/LoginClient", "/SigninClient"})
    public BaseResponse<JWTAuthResponse> Login(@Valid @RequestBody LoginRequest request)
    {
        return _userService.LoginWorkSpace(request);

    }
}

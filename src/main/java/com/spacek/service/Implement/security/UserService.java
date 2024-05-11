package com.spacek.service.Implement.security;

import com.spacek.Payload.Security.JWTAuthResponse;
import com.spacek.Payload.request.LoginRequest;
import com.spacek.Payload.request.LoginMobileRequest;
import com.spacek.Payload.request.RegisterRequest;
import com.spacek.Payload.response.BaseResponse;
import com.spacek.Payload.response.FailResponse;
import com.spacek.Payload.response.SuccessResponse;
import com.spacek.Repositery.security.IInfoTokenRepositery;
import com.spacek.Sceurity.JwtTokenProvider;
import com.spacek.Shard.Enums.UserRoleEnum;
import com.spacek.entity.security.InformationToken;
import com.spacek.entity.security.Role;
import com.spacek.entity.security.User;
import com.spacek.exception.BlogApiException;
import com.spacek.repositery.security.UserRepository;
import com.spacek.service.Implement.Notification.SmsService;
import com.spacek.service.Interface.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserRepository _userRepository;
    private final ModelMapper _mapper;
    private final PasswordEncoder _passwordEncoder;
    private final SmsService _smsService;
    private final AuthenticationManager _authenticationManager;
    private final JwtTokenProvider _tokenProvider;
    private final HttpServletRequest request;
    private final IInfoTokenRepositery _iInfoTokenRepositery;
    private final com.spacek.repositery.security.RoleRepository _roleRepository;

    public BaseResponse<RegisterRequest> CreateNewUser(RegisterRequest request) {
        if (_userRepository.existsByName(request.getUserName()))
            return new FailResponse<RegisterRequest>().setStatus("this user has Found");

        if (_userRepository.existsByEmail(request.getEmail()))
            return new FailResponse<RegisterRequest>().setStatus("this Email has Found");

        if (_userRepository.findByPhone(request.getPhone())
                .isPresent())
            return new FailResponse<RegisterRequest>().setStatus("This Number Already Exists");

        // send message
        // _smsService.send(model.getPhone(),"Should be confirm account by number");

        var result = _mapper.map(request, User.class);
        result.setPassword(_passwordEncoder.encode(request.getPassword()));

        User setUserRoleIsert = SetRoleUSer(result);
        _userRepository.save(setUserRoleIsert);

        return new SuccessResponse<RegisterRequest>().setStatus("Success Create User Will receive Message Mobile Phone ");
    }

    @Override
    public BaseResponse<String> RegisterClient() {
        return null;
    }

    private User SetRoleUSer(User obj) {
        Set<Role> roles = new HashSet<>();
        Role Dbrole = _roleRepository.findByName(UserRoleEnum.Default.name());
        roles.add(Dbrole);
        obj.setRoles(roles);
        return obj;
    }

    private InetAddress getIpUser() {
        InetAddress Ip = null;
        try {
            Ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return Ip;
    }



    public BaseResponse<JWTAuthResponse> LoginWorkSpace(LoginRequest model) {
        try {
            var result = _authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            model.getUserNameOrEmail(), model.getPassword()
                    ));
            SecurityContextHolder.getContext()
                    .setAuthentication(result);
            var s = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            var InfToken = saveRefreshToken(result);
            var res = new JWTAuthResponse(InfToken.getAccessToken(), InfToken.getRefreshToken());
            return new BaseResponse<JWTAuthResponse>("Success Login User", res) {
            };
        } catch (Exception e) {
            throw new BlogApiException(HttpStatus.NOT_FOUND, "Not Found This User");
        }
    }

    @Override
    public BaseResponse<String> CreateNewClient(LoginMobileRequest model) {
        _smsService.send(model.getPhone(), "Should be confirm account by number");
        return new BaseResponse<>("Should be confirm account by number") {
        };
    }

    @Override
    public ResponseEntity<JWTAuthResponse> mobileLogin(String userName, String phone) {
        return null;
    }

    private InformationToken saveRefreshToken(Authentication result) {
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        var model = new InformationToken();
        model.setAccessToken(_tokenProvider.GenrateToken(result, false));
        model.setRefreshToken(_tokenProvider.GenrateToken(result, true));
        model.setUserAgentText(userAgent);
        model.setRemoteIpAddress(request.getRemoteAddr()); // ip server
        model.setLocalIpAddress(getIpUser().getHostAddress()); // ip client
        return _iInfoTokenRepositery.save(model);
    }
}

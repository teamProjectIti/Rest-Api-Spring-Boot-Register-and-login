package com.spacek.Sceurity;

import com.spacek.service.Implement.security.CustomUserDetailsImplement;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtTokenProvider _TokenProvider;
    private CustomUserDetailsImplement _CustomUserDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsImplement CustomUserDetailsService) {
        _TokenProvider = jwtTokenProvider;
        _CustomUserDetailsService = CustomUserDetailsService;
    }

    private String GetJwtTokenFromRequest(HttpServletRequest request) {
        // get all header in request (token)
        String BearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(BearerToken) && BearerToken.startsWith("Bearer "))
            return BearerToken.substring(7, BearerToken.length());

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // get jwt token from request
        String Token = GetJwtTokenFromRequest(request);

        //validate token and is he authuncation or no
        if (StringUtils.hasText(Token) && _TokenProvider.validtionToken(Token)) {

            // get user name from token
            String UserName = _TokenProvider.GetUserNameFromJwt(Token);

            UserDetails userDetails = _CustomUserDetailsService.loadUserByUsername(UserName);

            UsernamePasswordAuthenticationToken AuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            AuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext()
                    .setAuthentication(AuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}

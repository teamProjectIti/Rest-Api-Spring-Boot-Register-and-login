package com.spacek.Sceurity;

import com.spacek.exception.BlogApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app-jwt-expiration-milliseconds}")

    private String jwtExpirationDate;
    @Value("${app-jwt-expiration-refresh-token}")
    private String REFRESH_TOKEN;

    public String GenrateToken(Authentication authentication, Boolean isRefresh) {
        // get roles users
        var authorities = authentication.getAuthorities();

        List<String> roles = authorities.stream()
                .map(x -> x.getAuthority())
                .collect(Collectors.toList());

//        var UserDetails=(AppUser)authentication.getPrincipal();

        String Token = Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setIssuer("app-server")
//                .setId(UserDetails.getId().toString())
                .claim("roles", roles)
                .setExpiration(CalcTokenExpertionDate(isRefresh))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

//              .setExpiration(ExpireDate)
//              .id(authentication.get)

        return Token;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Date CalcTokenExpertionDate(boolean isRefresh) {
        return new Date(System.currentTimeMillis() + (isRefresh ? Integer.parseInt(REFRESH_TOKEN) : Integer.parseInt(jwtExpirationDate)) * 1000);
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String GetUserNameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validtionToken(String Token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .build()
                    .parseSignedClaims(Token);
            return true;
        } catch (MalformedJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid jwt Token");
        } catch (ExpiredJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Expired Token Time");
        } catch (UnsupportedJwtException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "UnSupport jwt token");
        } catch (IllegalArgumentException e) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "jwt claims string is empty");
        }
    }
}

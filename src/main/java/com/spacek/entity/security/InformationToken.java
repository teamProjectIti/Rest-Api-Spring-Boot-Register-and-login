package com.spacek.entity.security;

import com.spacek.entity.contracts.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "InformationToken")
public class InformationToken extends BaseEntity {


    @NotBlank
    private String accessToken;
    @NotBlank
    private String refreshToken;
    private String userAgentText;
    private String localIpAddress;
    private String RemoteIpAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id",referencedColumnName = "ID")
    private User user;

    public InformationToken(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

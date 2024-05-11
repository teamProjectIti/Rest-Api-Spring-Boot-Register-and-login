package com.spacek.Repositery.security;

import com.spacek.entity.security.InformationToken;
import org.springframework.stereotype.Repository;

@Repository
public interface IInfoTokenRepositery extends com.spacek.repositery.BaseRepository<InformationToken, Long> {
    InformationToken findByRefreshToken(String accessToken);

}

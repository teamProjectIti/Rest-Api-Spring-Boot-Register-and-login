package com.spacek.repositery.security;

import com.spacek.entity.security.Otp;
import com.spacek.repositery.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends BaseRepository<Otp, Long>
{
    Optional<Otp> findFirstByMobile(String mobile);

}

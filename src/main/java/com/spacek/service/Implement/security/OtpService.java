package com.spacek.service.Implement.security;

import com.spacek.Payload.dto.OtpResponse;
import com.spacek.service.Implement.Notification.SmsService;
import com.spacek.entity.security.Otp;
import com.spacek.repositery.security.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final SmsService smsService;

    public Optional<Otp> getByMobile(String mobile) {
        return otpRepository.findFirstByMobile(mobile);
    }

    public Otp createOtp(String mobile) {
        Otp otp = this.getByMobile(mobile)
                .orElse(null);
        String code = String.valueOf(new Random().nextLong(10000L));
        if (otp == null) {
            otp = new Otp();
        }

        otp.setCode(code);
        otp.setMobile(mobile);
        otp.setExpiredAt(LocalDateTime.now()
                .plusHours(1));
        otp.setResentLeft(4);

        otpRepository.save(otp);
        return otp;
    }

    @Transactional
    public OtpResponse resend(String mobile) {
        Optional<Otp> otpOptional = this.getByMobile(mobile);
        Otp otp = otpOptional.orElse(null);
        OtpResponse otpResponse = new OtpResponse().setOtp(otp);

        if (otp == null) {
            otp = createOtp(mobile);
        }

        if (otp.isExpired()) {
            this.otpRepository.delete(otp);
            otp = createOtp(mobile);
        } else if (otp.isExceeded()) {
            return otpResponse.setError("you have exceeded your tries");
        } else {
            otp.setResentLeft(otp.getResentLeft() - 1);
            this.otpRepository.save(otp);
        }

        String code = String.valueOf(new Random().nextLong(10000L));

        otp.setCode(code);
        this.otpRepository.save(otp);

        String msg = "Your OTP: " + code; // Assuming this is your SMS message format

        smsService.send(mobile, msg);

        return otpResponse.setOtp(otp);
    }

    public OtpResponse verify(String mobile, String code) {
        Optional<Otp> otpOptional = this.getByMobile(mobile);
        Otp otp = otpOptional.orElse(null);
        OtpResponse otpResponse = new OtpResponse().setOtp(otp);

        if (otp == null) {
            return otpResponse.setError("رمز التحقق غير صالح");
        }

        if (otp.isExpired()) {
            return otpResponse.setError("رقم التحقق منتهي");
        }

        if (!otp.hasWrongCount()) {
            return otpResponse.setError("انتهت عدد المحاولات");
        }

        if (otp.getCode() != code) {
            if (otp.hasWrongCount()) {
                otp.setWrongCount(otp.getWrongCount() - 1);
                this.otpRepository.save(otp);
            }
            return otpResponse.setError("رمز التحقق غير صالح");
        }

        return otpResponse;
    }
}

package com.spacek.entity.security;

import com.spacek.entity.contracts.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
@Setter
@Getter
public class Otp extends BaseEntity {
    private String mobile;
    private String code;

    @ColumnDefault("4")
    private Integer resentLeft;
    @ColumnDefault("0")
    private Integer wrongCount;
    private LocalDateTime expiredAt;

    public Boolean isExpired() {
        return this.expiredAt.isBefore(LocalDateTime.now());
    }

    public Boolean isExceeded() {
        return this.resentLeft <= 0;
    }

    public Boolean hasWrongCount() {
        return this.wrongCount > 0;
    }
}

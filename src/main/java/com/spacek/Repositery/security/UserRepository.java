package com.spacek.repositery.security;

import com.spacek.entity.contracts.BaseEntity;
import com.spacek.entity.security.User;
import com.spacek.repositery.BaseRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);
    Optional<User> findByName(String Name);
    Boolean existsByName(String Name);
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone); // Change the return type to Optional<User>


}

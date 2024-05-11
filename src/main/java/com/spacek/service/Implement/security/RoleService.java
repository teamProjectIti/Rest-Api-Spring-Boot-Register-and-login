package com.spacek.service.Implement.security;


import com.spacek.repositery.security.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService  {
    private final RoleRepository roleRepository;
}

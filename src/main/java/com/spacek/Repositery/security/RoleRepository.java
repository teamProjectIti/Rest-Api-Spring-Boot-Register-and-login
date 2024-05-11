package com.spacek.repositery.security;

import com.spacek.entity.security.Role;
import com.spacek.repositery.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long>
{
    Role findByName(String Name);

}
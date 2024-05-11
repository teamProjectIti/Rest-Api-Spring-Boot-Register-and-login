package com.spacek.repositery;

import com.spacek.entity.contracts.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface BaseRepository<T extends BaseEntity, ID extends Number> extends JpaRepository<T, ID> {
}

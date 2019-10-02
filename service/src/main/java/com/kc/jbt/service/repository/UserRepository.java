package com.kc.jbt.service.repository;

import com.kc.jbt.service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

}

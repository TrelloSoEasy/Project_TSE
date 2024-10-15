package com.sparta.tse.domain.user.repository;

import com.sparta.tse.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

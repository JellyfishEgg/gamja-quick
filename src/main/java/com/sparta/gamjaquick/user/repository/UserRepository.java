package com.sparta.gamjaquick.user.repository;

import com.sparta.gamjaquick.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

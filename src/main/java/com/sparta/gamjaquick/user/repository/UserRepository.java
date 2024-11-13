package com.sparta.gamjaquick.user.repository;

import com.sparta.gamjaquick.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일로 사용자 조회
    Optional<User> findByEmail(String email);

    // 사용자 이름으로 조회
    Optional<User> findByUsername(String username);

    // 닉네임으로 사용자 조회
    Optional<User> findByNickname(String nickname);

    // 연락처로 사용자 조회
    Optional<User> findByPhoneNumber(String phoneNumber);

    // 특정 ID로 조회하되, 삭제되지 않은 사용자만 조회
    Optional<User> findByIdAndIsDeletedFalse(Long id);
}
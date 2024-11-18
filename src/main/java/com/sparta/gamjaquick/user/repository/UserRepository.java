package com.sparta.gamjaquick.user.repository;

import com.sparta.gamjaquick.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository
 * 사용자 데이터를 관리하는 JPA Repository
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * 이메일로 사용자 조회
     * @param email 사용자 이메일
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);

    /**
     * 사용자 이름으로 조회
     * @param username 사용자 이름
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);

    /**
     * 닉네임으로 사용자 조회
     * @param nickname 사용자 닉네임
     * @return Optional<User>
     */
    Optional<User> findByNickname(String nickname);

    /**
     * 연락처로 사용자 조회
     * @param phoneNumber 사용자 연락처
     * @return Optional<User>
     */
    Optional<User> findByPhoneNumber(String phoneNumber);

    /**
     * 특정 ID로 조회하되, 삭제되지 않은 사용자만 조회
     * @param id 사용자 ID
     * @return Optional<User>
     */
    Optional<User> findByIdAndIsDeletedFalse(Long id);

    /**
     * 페이징 및 검색 조건에 맞는 사용자 조회
     * @param username 사용자 이름 (검색 조건)
     * @param email 이메일 (검색 조건)
     * @param phoneNumber 연락처 (검색 조건)
     * @param pageable 페이징 및 정렬 정보
     * @return Page<User>
     */
    Page<User> findByUsernameContainingOrEmailContainingOrPhoneNumberContaining(
            String username,
            String email,
            String phoneNumber,
            Pageable pageable
    );
}
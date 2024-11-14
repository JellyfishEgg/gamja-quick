package com.sparta.gamjaquick.user.controller;

import com.sparta.gamjaquick.user.dto.request.UserSignUpRequestDto;
import com.sparta.gamjaquick.user.dto.request.UserUpdateRequestDto;
import com.sparta.gamjaquick.user.dto.response.UserResponseDto;
import com.sparta.gamjaquick.user.dto.response.UserDeleteResponseDto;
import com.sparta.gamjaquick.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "유저 관련 API")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원 가입
    @PostMapping("/signup")
    public UserResponseDto registerUser(@RequestBody UserSignUpRequestDto signUpDto) {
        return userService.registerUser(signUpDto);
    }

    // 특정 사용자 조회
    @GetMapping("/{user_id}")
    public UserResponseDto getUserById(@PathVariable("user_id") Long id) {
        return userService.getUserById(id);
    }

    // 전체 사용자 조회 (관리자용)
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // 사용자 정보 수정
    @PutMapping("/{user_id}")
    public UserResponseDto updateUser(@PathVariable("user_id") Long id, @RequestBody UserUpdateRequestDto updateDto) {
        return userService.updateUser(id, updateDto);
    }

    // 사용자 삭제 (회원 탈퇴)
    @DeleteMapping("/{user_id}")
    public UserDeleteResponseDto deleteUser(@PathVariable("user_id") Long id) {
        // 삭제자를 관리자로 지정.
        return userService.deleteUser(id, "admin");
    }
}
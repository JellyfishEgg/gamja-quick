package com.sparta.gamjaquick.user.controller;

import com.sparta.gamjaquick.common.response.ApiResponseDto;
import com.sparta.gamjaquick.common.response.MessageType;
import com.sparta.gamjaquick.user.dto.request.UserSearchParameter;
import com.sparta.gamjaquick.user.dto.request.UserSignUpRequestDto;
import com.sparta.gamjaquick.user.dto.request.UserUpdateRequestDto;
import com.sparta.gamjaquick.user.dto.response.UserResponseDto;
import com.sparta.gamjaquick.user.dto.response.UserDeleteResponseDto;
import com.sparta.gamjaquick.user.entity.User;
import com.sparta.gamjaquick.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(summary = "회원가입", description = "회원가입 할 때 사용하는 API")
    @PostMapping("/signup")
    public UserResponseDto registerUser(@RequestBody UserSignUpRequestDto signUpDto) {
        return userService.registerUser(signUpDto);
    }

    // 특정 사용자 조회
    @Operation(summary = "특정 사용자 조회", description = "특정 사용자를 조회 할 때 사용하는 API")
    @GetMapping("/{user_id}")
    @PreAuthorize("hasRole('ADMIN') or (#user_id == authentication.principal.id)")  // 관리자나 자기 자신만 조회 가능
    public UserResponseDto getUserById(@PathVariable("user_id") Long id) {
        return userService.getUserById(id);
    }

    // 전체 사용자 조회 (관리자용)
    @Operation(summary = "전체 사용자 조회 (관리자용)", description = "관리자가 전체 사용자를 조회 할 때 사용하는 API")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }

    // 사용자 정보 수정
    @Operation(summary = "사용자 정보 수정", description = "사용자가 정보를 수정 할 때 사용하는 API")
    @PutMapping("/{user_id}")
    @PreAuthorize("hasRole('ADMIN') or (#user_id == authentication.principal.id)")  // 관리자나 자기 자신만 수정 가능
    public UserResponseDto updateUser(@PathVariable("user_id") Long id, @RequestBody UserUpdateRequestDto updateDto) {
        return userService.updateUser(id, updateDto);
    }

    // 사용자 삭제 (회원 탈퇴)
    @Operation(summary = "회원 탈퇴", description = "사용자가 회원 탈퇴를 할 때 사용하는 API")
    @DeleteMapping("/{user_id}")
    @PreAuthorize("hasRole('ADMIN') or (#user_id == authentication.principal.id)")  // 관리자나 자기 자신만 삭제 가능
    public UserDeleteResponseDto deleteUser(@PathVariable("user_id") Long id) {
        return userService.deleteUser(id, "admin");  // 삭제자는 "admin"
    }

    // 사용자 검색
    @Operation(summary = "사용자 검색", description = "사용자를 검색 할 때 사용하는 API")
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')") // 관리자가 접근할 수 있도록 설정
    public ResponseEntity<ApiResponseDto> searchUsers(@ModelAttribute UserSearchParameter searchParam,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Page<User> userPage = userService.searchUsers(searchParam, page, size);
        return ResponseEntity.ok(ApiResponseDto.success(MessageType.RETRIEVE, userPage));
    }
}
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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User", description = "유저 관련 API")
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원 가입
     * @param signUpDto 사용자 회원가입 정보를 담은 DTO
     * @return ApiResponseDto<UserResponseDto>
     */
    @Operation(summary = "회원가입", description = "회원가입 할 때 사용하는 API")
    @PostMapping("/signup")
    public ApiResponseDto<UserResponseDto> registerUser(@RequestBody UserSignUpRequestDto signUpDto) {
        return ApiResponseDto.success(MessageType.CREATE, userService.registerUser(signUpDto));
    }

    /**
     * 특정 사용자 조회
     * @param id 사용자 ID (PathVariable로 전달받음)
     * @return ApiResponseDto<UserResponseDto>
     */
    @Operation(summary = "특정 사용자 조회", description = "특정 사용자를 조회 할 때 사용하는 API")
    @Parameter(name = "userId", description = "유저 ID", example = "1")
    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER') or hasRole('OWNER')")
    public ApiResponseDto<UserResponseDto> getUserById(@PathVariable("userId") Long id) {
        return ApiResponseDto.success(MessageType.RETRIEVE, userService.getUserById(id));
    }

    /**
     * 전체 사용자 조회(Admin 전용)
     * 모든 사용자 정보를 조회합니다.
     * @return ApiResponseDto<List<UserResponseDto>>
     */
    @Operation(summary = "전체 사용자 조회 (관리자용)", description = "관리자가 전체 사용자를 조회 할 때 사용하는 API")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponseDto<List<UserResponseDto>> getAllUsers() {
        return ApiResponseDto.success(MessageType.RETRIEVE, userService.getAllUsers());
    }

    /**
     * 사용자 정보 수정
     * @param id 사용자 ID
     * @param updateDto 수정할 사용자 정보가 담긴 DTO
     * @return ApiResponseDto<UserResponseDto>
     */
    @Operation(summary = "사용자 정보 수정", description = "사용자가 정보를 수정 할 때 사용하는 API")
    @Parameter(name = "userId", description = "유저 ID", example = "1")
    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('OWNER')")
    public ApiResponseDto<UserResponseDto> updateUser(@PathVariable("userId") Long id, @RequestBody UserUpdateRequestDto updateDto) {
        return ApiResponseDto.success(MessageType.UPDATE, userService.updateUser(id, updateDto));
    }

    /**
     * 사용자 삭제(회원 탈퇴)
     * 소프트 삭제 방식으로 isDeleted 필드를 true로 설정
     * @param id 사용자 ID
     * @return ApiResponseDto<UserDeleteResponseDto>
     */
    @Operation(summary = "회원 탈퇴", description = "사용자가 회원 탈퇴를 할 때 사용하는 API")
    @Parameter(name = "userId", description = "유저 ID", example = "1")
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    public ApiResponseDto<UserDeleteResponseDto> deleteUser(@PathVariable("userId") Long id) {
        return ApiResponseDto.success(MessageType.DELETE, userService.deleteUser(id, "admin"));
    }

    /**
     * 사용자 검색 및 페이징 처리(관리자 전용)
     * @param searchParam 검색 조건을 담은 DTO
     * @param page 요청한 페이지 번호
     * @param size 페이지당 항목 수
     * @return ApiResponseDto 검색 결과
     */
    @Operation(summary = "사용자 검색", description = "사용자를 검색 할 때 사용하는 API")
    @Parameters(
            {
                    @Parameter(name = "searchParam", description = "검색 조건"),
                    @Parameter(name = "page", description = "페이지", example = "1"),
                    @Parameter(name = "size", description = "한 페이지에 보일 사용자 수", example = "10")
            }
    )
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDto> searchUsers(@ModelAttribute UserSearchParameter searchParam,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        Page<User> userPage = userService.searchUsers(searchParam, page, size);
        return ResponseEntity.ok(ApiResponseDto.success(MessageType.RETRIEVE, userPage));
    }
}
package com.sparta.tse.domain.user.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.user.dto.DeleteUserRequestDto;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원 탈퇴
    @Transactional
    public void deletedUser(AuthUser authUser, DeleteUserRequestDto deleteUserRequestDto) {
        // authUser 이메일로 현재 로그인한 User 찾기
        User user = userRepository.findByEmail(authUser.getEmail())
                .orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_USER));

        // 회원의 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(deleteUserRequestDto.getPassword(), user.getPassword())){
            throw new ApiException(ErrorStatus._PASSWORD_NOT_MATCHES);
        }

        // 이미 탈퇴한 회원인지 확인
        if (user.getIsdeleted()){
            throw new ApiException(ErrorStatus._DELETED_USER);
        }

        // 회원 탈퇴 메소드
        user.deletedUser(user.getEmail(), user.getPassword());
        // 변경된 내용 저장
        userRepository.save(user);
    }
}

package com.sparta.tse.domain.user.service;

import com.sparta.tse.common.entity.ErrorStatus;
import com.sparta.tse.common.exception.ApiException;
import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원 탈퇴
    public void deletedUser(String email, String password) {
        // 존재하는 회원인지 확인
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new ApiException(ErrorStatus._NOT_FOUND_USER));

        // 회원의 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(password,user.getPassword())){
            throw new ApiException(ErrorStatus._PASSWORD_NOT_MATCHES);
        }

        // 이미 탈퇴한 회원인지 확인
        if (user.getIsdeleted()){
            throw new ApiException(ErrorStatus._DELETED_USER);
        }

        // 회원 탈퇴 메소드
        user.deletedUser(email,password);
    }
}

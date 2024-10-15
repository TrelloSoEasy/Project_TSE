package com.sparta.tse.domain.user.entity;

import com.sparta.tse.common.entity.Timestamped;
import com.sparta.tse.config.AuthUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, length = 256,nullable = false)
    private String email;

    @Column(length = 100,nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    // 유저 생성자
    public User (String email, String nickname,String password, UserRole userRole){
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.userRole = userRole;
    }

    // AuthUser 로 User 를 만들 때
    public User (String email, String nickname,UserRole userRole){
        this.email = email;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    // 유저 생성 AuthUser 로 하는방식
    public static User fromAuthUser(AuthUser authUser){
        return new User(authUser.getEmail(),authUser.getEmail(),authUser.getUserRole());
    }



}

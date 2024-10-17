package com.sparta.tse.domain.user.entity;

import com.sparta.tse.common.entity.Timestamped;
import com.sparta.tse.config.AuthUser;
import com.sparta.tse.domain.card_member.entity.CardMember;
import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.comment.entity.CardComment;
import com.sparta.tse.domain.user.enums.UserRole;
import com.sparta.tse.domain.workspaceMember.entity.WorkspaceMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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


    // 회원탈퇴 유무
    private Boolean isdeleted = false;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<WorkspaceMember> workspaceMembers = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<CardMember> cardMembers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardComment> comment = new ArrayList<>();


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
    // 회원 탈퇴 메소드
    public void deletedUser (String email, String password){
        this.isdeleted = true;
    }


}

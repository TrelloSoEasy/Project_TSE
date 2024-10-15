package com.sparta.tse.domain.invitation.entity;

import com.sparta.tse.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;

@Getter
@Entity
@NoArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitationId;

    @ManyToOne
    @JoinColumn(name="workspace_id")
    private Workspace workspace;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User sendingUser;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User receivingUser;

    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;

    public Invitation(User sendingUser, User receivingUser) {
        this.sendingUser = sendingUser;
        this.receivingUser = receivingUser;
        this.invitationStatus = InvitationStatus.Pending;
    }

    public rejectInvitation() {

    }
}

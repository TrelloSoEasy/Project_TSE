package com.sparta.tse.domain.invitation.entity;

import com.sparta.tse.domain.user.entity.User;
import com.sparta.tse.domain.workspace.entity.Workspace;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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
    @JoinColumn(name="sendinguser_id")
    private User sendingUser;

    @ManyToOne
    @JoinColumn(name="receivinguser_id")
    private User receivingUser;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private InvitationStatus invitationStatus;

    public Invitation(User sendingUser, User receivingUser,Workspace workspace) {
        this.sendingUser = sendingUser;
        this.receivingUser = receivingUser;
        this.invitationStatus = InvitationStatus.Pending;
        this.workspace = workspace;
    }

    public void acceptInvitation() {
        this.invitationStatus = InvitationStatus.Accepted;
    }

    public void rejectInvitation() {
        this.invitationStatus = InvitationStatus.rejected;
    }
}

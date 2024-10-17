package com.sparta.tse.domain.invitation.repository;


import com.sparta.tse.domain.invitation.entity.Invitation;
import com.sparta.tse.domain.invitation.entity.InvitationStatus;
import com.sparta.tse.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    @Query("SELECT i FROM Invitation i where i.receivingUser=:receivingUser and i.sendingUser = :sendingUser"
    +" and i.invitationStatus = :status and i.workspace.workspaceId=:workspaceId order by i.createdAt desc")
    Invitation findByReceivingUserAndSendingUserAndInvitationStatusAndWorkspaceId(User receivingUser,
                                                                                  User sendingUser,
                                                                                  InvitationStatus status,
                                                                                  Long workspaceId);

    @Query("SELECT i from Invitation i where i.receivingUser=:receivingUser and i.invitationStatus=:status" +
            " and i.workspace.workspaceId=:workspaceId order by i.createdAt desc")
    List<Invitation> findByReceivingUserAndInvitationStatusAndWorkspaceId(User receivingUser,
                                                                          InvitationStatus status,
                                                                          Long workspaceId);
    @Query("SELECT i from Invitation i where i.receivingUser=:receivingUser and i.invitationStatus=:status" +
            " order by i.createdAt desc")
    List<Invitation> findByReceivingUser(User receivingUser,
                                         InvitationStatus status);
}

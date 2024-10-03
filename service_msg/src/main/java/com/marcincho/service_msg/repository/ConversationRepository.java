package com.marcincho.service_msg.repository;

import com.marcincho.service_msg.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, UUID> {

    @Query("SELECT c FROM Conversation c WHERE c.toUser = :toUser and c.deliveryStatus in ('NOT_DELIVERED', 'DELIVERED') and c.fromUser = :fromUser")
    List<Conversation> findUnseen(@Param("toUser")UUID toUser, @Param("fromUser")UUID fromUser);

    @Query("SELECT c FROM Conversation c WHERE c.toUser = :toUser and c.deliveryStatus in ('NOT_DELIVERED', 'DELIVERED')")
    List<Conversation> findUnseenMessagesCount(@Param("toUser") UUID toUser);

}

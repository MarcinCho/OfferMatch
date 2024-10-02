package com.marcincho.service_msg.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    @Id
    private UUID id;

    private String convId;

    private UUID fromUser;

    private UUID toUser;

    private Date time;

    private Date lastModified;

    private String content;

    private String deliveryStatus;



}

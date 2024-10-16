package com.marcincho.service_msg.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserConnection {

    private UUID connectionId;
    private String connectionUsername;
    private String convID;
    private int unSeen;

    @JsonProperty("isOnline")
    private boolean isOnline;

}

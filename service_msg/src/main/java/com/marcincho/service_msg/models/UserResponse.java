package com.marcincho.service_msg.models;

import java.util.UUID;

public record UserResponse(UUID id, String username, String email) {
}

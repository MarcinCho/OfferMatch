package com.marcincho.service_msg.models;

import java.util.Set;

public record SignupRequest (String username, String email, Set<String> role, String password) {
}

package com.coders.bank.dto;

import com.coders.bank.constants.Role;

public record UserResponse(
        String name,
        String email,
        Role role
) {
}

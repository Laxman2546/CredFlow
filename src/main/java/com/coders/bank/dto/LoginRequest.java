package com.coders.bank.dto;

public record LoginRequest(
        String email,
        String password
) {
}

package com.coders.bank.dto;

import com.coders.bank.constants.Role;

public record RegisterRequest (
        String name,
        String email,
        String password
 ){

}

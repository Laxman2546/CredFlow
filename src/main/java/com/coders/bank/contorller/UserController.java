package com.coders.bank.contorller;

import com.coders.bank.Service.JwtService;
import com.coders.bank.Service.UserService;
import com.coders.bank.dto.AuthResponse;
import com.coders.bank.dto.LoginRequest;
import com.coders.bank.dto.RegisterRequest;
import com.coders.bank.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }
    @PostMapping("/createuser")
    public ResponseEntity<UserResponse>  createUser(@RequestBody RegisterRequest registerRequest){
            return  ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.createUser(registerRequest));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody LoginRequest loginRequest){
        System.out.println(loginRequest.email() +" "+loginRequest.password());
        return  ResponseEntity.ok().body(userService.loginUser(loginRequest));
    }
    @GetMapping("/getall")
    public List<UserResponse> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam String token){
        return  jwtService.extractUserName(token);
    }
}

package com.coders.bank.Service;

import com.coders.bank.Model.UserModel;
import com.coders.bank.Repository.UserRepository;
import com.coders.bank.constants.Role;
import com.coders.bank.dto.AuthResponse;
import com.coders.bank.dto.LoginRequest;
import com.coders.bank.dto.RegisterRequest;
import com.coders.bank.dto.UserResponse;
import com.coders.bank.exception.InvalidCredentialException;
import com.coders.bank.exception.UserAlreadyExists;
import com.coders.bank.exception.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.coders.bank.Model.UserModel.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public UserResponse createUser(RegisterRequest registerRequest){
        if(userRepository.existsByEmail(registerRequest.email())){
            throw new UserAlreadyExists("User already exists");
        }
        UserModel user = UserModel.builder()
                .name(registerRequest.name())
                .email(registerRequest.email())
                .password(passwordEncoder.encode(registerRequest.password()))
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);
        return new UserResponse(user.getName(),user.getEmail(),user.getRole());
    }
    public List<UserResponse> getAllUsers(){
         return userRepository.findAll().stream().
                 map(userModel ->  new UserResponse(userModel.getName(),userModel.getEmail(),Role.CUSTOMER))
                 .collect(Collectors.toList());
    }



    public AuthResponse loginUser(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(),loginRequest.password()));
        UserModel user = userRepository
                .findByEmail(loginRequest.email())
                .orElseThrow(() ->
                        new UserNotFound("User not found"));

        if(!passwordEncoder.matches(loginRequest.password(),user.getPassword())){
            throw new InvalidCredentialException("Invalid Email or Password");
        }
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}

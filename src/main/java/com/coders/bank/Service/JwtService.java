package com.coders.bank.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtKey;
    public String generateToken(UserDetails user){
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24))
                .signWith(getSignKey())
                .compact();
    }

    private SecretKey getSignKey(){
        return Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));
    }
    public String extractUserName(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public Date expirationTime(String token){
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }
    public boolean isValidToken(String token , UserDetails user ){
        String username = extractUserName(token);
         return username.equals(user.getUsername()) && !expirationTime(token).before(new Date()) ;
    }

}

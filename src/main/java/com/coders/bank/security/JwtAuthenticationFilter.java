package com.coders.bank.security;

import com.coders.bank.Service.CustomerDetailsService;
import com.coders.bank.Service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomerDetailsService customerDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }
        String jwt = authHeader.substring(7);
        String email = jwtService.extractUserName(jwt);
        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails user = customerDetailsService.loadUserByUsername(email);
                boolean res = jwtService.isValidToken(jwt,user);
                if(res){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }else{
                    filterChain.doFilter(request,response);
                }
        }
        filterChain.doFilter(request,response);

    }
}

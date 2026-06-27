package com.coders.bank.Model;


import com.coders.bank.constants.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Entity
@Builder
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final String name;
    @Column(unique = true)
    private final String email;
    private final String password;
    private final Role role;
    @CreationTimestamp
    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createAt;
    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        assert role != null;
        return List.of(new SimpleGrantedAuthority("Role"+role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

}


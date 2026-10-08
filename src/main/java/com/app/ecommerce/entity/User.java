package com.app.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;              // @Builder.Default → USER

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();  // @Builder.Default → now

    public enum Role {
        USER, ADMIN
    }

    // ✅ Required by JPA
    public User() {}

    // ✅ Private constructor used by Builder
    private User(Builder builder) {
        this.name      = builder.name;
        this.email     = builder.email;
        this.password  = builder.password;
        this.role      = builder.role;
        this.createdAt = builder.createdAt;
        // id is NOT set — database auto-generates it
    }

    // ✅ Entry point
    public static Builder builder() {
        return new Builder();
    }

    // ✅ Getters
    public Long getId()                { return id; }
    public String getName()            { return name; }
    public String getEmail()           { return email; }
    public Role getRole()              { return role; }
    public LocalDateTime getCreatedAt(){ return createdAt; }

    // ✅ Setters
    public void setId(Long id)                        { this.id = id; }
    public void setName(String name)                  { this.name = name; }
    public void setEmail(String email)                { this.email = email; }
    public void setPassword(String password)          { this.password = password; }
    public void setRole(Role role)                    { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // ============================
    //  UserDetails Methods
    // ============================
    @Override
    public String getUsername() { return email; }   // email is the username

    @Override
    public String getPassword() { return password; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }

    // ============================
    //  BUILDER CLASS
    // ============================
    public static class Builder {

        private String name;
        private String email;
        private String password;
        private Role role = Role.USER;                    // @Builder.Default → USER
        private LocalDateTime createdAt = LocalDateTime.now(); // @Builder.Default → now

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

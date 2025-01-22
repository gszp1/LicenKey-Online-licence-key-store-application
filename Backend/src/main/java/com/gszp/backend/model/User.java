package com.gszp.backend.model;

import com.gszp.backend.auth.UserRole;
import com.gszp.backend.auth.UserRoleConverter;
import com.gszp.backend.auth.UserStatus;
import com.gszp.backend.auth.UserStatusConverter;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(length = 320, nullable = false, unique = true)
    private String email;

    @Column(length = 256, nullable = false)
    private String passwordHash;

    @Column(length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Convert(converter = UserStatusConverter.class)
    @Column(name = "user_status")
    private UserStatus userStatus;

    @Convert(converter = UserRoleConverter.class)
    @Column(name = "user_role")
    private UserRole userRole;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = Boolean.FALSE;

    @Column(name = "deactivation_date", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime deactivationDate;

    @Column(name = "creation_date", columnDefinition = "TIMESTAMPTZ")
    private OffsetDateTime creationDate;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Key> keys = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<ShoppingCart> shoppingCartEntries = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return userStatus != UserStatus.DEACTIVATED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return userStatus != UserStatus.BANNED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userStatus == UserStatus.ACTIVE;
    }
}

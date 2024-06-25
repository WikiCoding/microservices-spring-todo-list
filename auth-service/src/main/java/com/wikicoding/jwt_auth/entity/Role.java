package com.wikicoding.jwt_auth.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wikicoding.jwt_auth.entity.Permission.*;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE
            )
    ),
    ASSISTANT(
            Set.of(
                    ASSISTANT_READ,
                    ASSISTANT_UPDATE,
                    ASSISTANT_DELETE,
                    ASSISTANT_CREATE
            )
    ),
    ORGANIZER(
            Set.of(
                    ORGANIZER_READ,
                    ORGANIZER_UPDATE,
                    ORGANIZER_CREATE,
                    ORGANIZER_DELETE
            )
    ),
    SECURITY(
            Set.of(
                    SECURITY_READ,
                    SECURITY_UPDATE,
                    SECURITY_CREATE,
                    SECURITY_DELETE
            )
    ),
    VISITANT(
            Set.of(
                    VISITANT_READ,
                    VISITANT_UPDATE,
                    VISITANT_CREATE,
                    VISITANT_DELETE
            )
    )

    ;

    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}

package com.wikicoding.jwt_auth.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    ASSISTANT_READ("assistant:read"),
    ASSISTANT_UPDATE("assistant:update"),
    ASSISTANT_CREATE("assistant:create"),
    ASSISTANT_DELETE("assistant:delete"),
    ORGANIZER_READ("organizer:read"),
    ORGANIZER_UPDATE("organizer:update"),
    ORGANIZER_CREATE("organizer:create"),
    ORGANIZER_DELETE("organizer:delete"),
    SECURITY_READ("security:read"),
    SECURITY_UPDATE("security:update"),
    SECURITY_CREATE("security:create"),
    SECURITY_DELETE("security:delete"),
    VISITANT_READ("visitant:read"),
    VISITANT_UPDATE("visitant:update"),
    VISITANT_CREATE("visitant:create"),
    VISITANT_DELETE("visitant:delete");


    private final String permission;
}

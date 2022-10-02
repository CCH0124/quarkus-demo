package org.cch.definition;

import org.cch.exception.AuthenticationRoleException;

import java.util.Objects;
import java.util.stream.Stream;

public enum ERole {
    USER("USER"),
    MODERATOR("MODERATOR"),
    ADMIN("ADMIN");

    private final String value;

    ERole(final String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static ERole byValue(String value) throws AuthenticationRoleException {
        return Stream.of(ERole.values()).filter(e -> Objects.equals(e.getValue(), value)).findFirst().orElseThrow(AuthenticationRoleException::new);
    }


}

package br.com.albumfigurinha.api.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {
    ADMIN("Administrator"),
    AUTHOR("Author"),
    COLLECTOR("Collector");

    private final String description;

    public static boolean isValidRole(String role) {
        try {
            Roles.valueOf(role);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

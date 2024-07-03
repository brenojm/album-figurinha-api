package br.com.albumfigurinha.api.dto;

import br.com.albumfigurinha.api.entity.enums.Roles;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserCreationDTO {
    @NotNull
    private String fullName;
    @NotNull
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String role;
}

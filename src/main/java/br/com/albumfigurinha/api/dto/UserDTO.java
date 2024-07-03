package br.com.albumfigurinha.api.dto;

import br.com.albumfigurinha.api.entity.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String fullName;
    private String userName;
    private Roles role;
}

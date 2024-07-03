package br.com.albumfigurinha.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignInDTO {
    private String username;
    private String password;
}

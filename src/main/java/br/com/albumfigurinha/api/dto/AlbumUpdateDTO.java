package br.com.albumfigurinha.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AlbumUpdateDTO {

    @NotNull
    private String name;
    @NotNull
    private int pageNumber;

}

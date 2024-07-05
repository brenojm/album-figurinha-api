package br.com.albumfigurinha.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StickerUpdateDTO {

    @NotNull
    String id;
    @NotNull
    String name;
    @NotNull
    int page;
    @NotNull
    String description;

}

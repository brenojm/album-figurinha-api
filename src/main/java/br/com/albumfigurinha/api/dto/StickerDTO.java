package br.com.albumfigurinha.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StickerDTO {
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private int page;
    @NotNull
    private String description;
    @NotNull
    private String tag;
}

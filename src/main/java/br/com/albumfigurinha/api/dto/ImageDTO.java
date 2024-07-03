package br.com.albumfigurinha.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageDTO {
//    private String id;

    private String name;

    private String type;

    private String md5;
}

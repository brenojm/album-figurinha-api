package br.com.albumfigurinha.api.mapper;

import br.com.albumfigurinha.api.dto.ImageDTO;
import br.com.albumfigurinha.api.dto.UserCreationDTO;
import br.com.albumfigurinha.api.dto.UserDTO;
import br.com.albumfigurinha.api.entity.Image;
import br.com.albumfigurinha.api.entity.User;
import br.com.albumfigurinha.api.entity.enums.Roles;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {


    public ImageDTO toDto(Image image) {
        return new ImageDTO(image.getName(), image.getType(), image.getMd5());
    }


}

package br.com.albumfigurinha.api.mapper;

import br.com.albumfigurinha.api.dto.StickerDTO;
import br.com.albumfigurinha.api.dto.UserCreationDTO;
import br.com.albumfigurinha.api.dto.UserDTO;
import br.com.albumfigurinha.api.entity.Sticker;
import br.com.albumfigurinha.api.entity.User;
import br.com.albumfigurinha.api.entity.enums.Roles;
import org.springframework.stereotype.Component;

@Component
public class StickerMapper {


    public StickerDTO toDto(Sticker sticker) {
        return new StickerDTO(sticker.getId(), sticker.getName(), sticker.getPage(), sticker.getDescription(), sticker.getStickerImage().getMd5());
    }


}

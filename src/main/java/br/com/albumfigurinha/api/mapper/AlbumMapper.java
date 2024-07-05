package br.com.albumfigurinha.api.mapper;

import br.com.albumfigurinha.api.dto.AlbumDTO;
import br.com.albumfigurinha.api.dto.ImageDTO;
import br.com.albumfigurinha.api.entity.Album;
import br.com.albumfigurinha.api.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class AlbumMapper {

    public AlbumDTO toDto(Album album) {
        return new AlbumDTO(album.getId(), album.getName(),album.getPageNumber(), album.getAlbumCover().getMd5());
    }


}

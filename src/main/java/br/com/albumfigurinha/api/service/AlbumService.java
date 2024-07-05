package br.com.albumfigurinha.api.service;

import br.com.albumfigurinha.api.dto.AlbumUpdateDTO;
import br.com.albumfigurinha.api.entity.Album;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AlbumService {
    Album updateAlbum(AlbumUpdateDTO albumDto, MultipartFile coverFile) throws IOException;
    Album getAlbum();
}

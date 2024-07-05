package br.com.albumfigurinha.api.service.impl;

import br.com.albumfigurinha.api.dto.AlbumUpdateDTO;
import br.com.albumfigurinha.api.entity.Album;
import br.com.albumfigurinha.api.repository.AlbumRepository;
import br.com.albumfigurinha.api.service.AlbumService;
import br.com.albumfigurinha.api.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@AllArgsConstructor
public class AlbumServiceImpl implements AlbumService {

    private final ImageService imageService;
    private AlbumRepository albumRepository;

    @Override
    public Album updateAlbum(AlbumUpdateDTO albumDto, MultipartFile coverFile) throws IOException {
        Album existingAlbum = albumRepository.findAll().stream().findFirst().get();
        if (!albumDto.getName().isBlank() && !albumDto.getName().isEmpty()) {
            existingAlbum.setName(albumDto.getName());
        }
        if (albumDto.getPageNumber() > 0) {
            existingAlbum.setPageNumber(albumDto.getPageNumber());
        }

        String md5ToDelete = "";
        if (!coverFile.isEmpty()) {
            if (!imageService.imageExists(existingAlbum.getAlbumCover().getMd5())) {
                var cover = imageService.uploadImage(coverFile);
                md5ToDelete = existingAlbum.getAlbumCover().getMd5();
                existingAlbum.setAlbumCover(cover);
            }
        }

        albumRepository.save(existingAlbum);
        if (!md5ToDelete.isEmpty()) {
            imageService.removeImage(md5ToDelete);
        }


        return existingAlbum;
    }

    @Override
    public Album getAlbum() {
        return albumRepository.findAll().stream().findFirst().get();
    }
}


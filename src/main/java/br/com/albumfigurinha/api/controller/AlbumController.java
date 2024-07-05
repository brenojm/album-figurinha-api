package br.com.albumfigurinha.api.controller;

import br.com.albumfigurinha.api.dto.AlbumDTO;
import br.com.albumfigurinha.api.dto.AlbumUpdateDTO;
import br.com.albumfigurinha.api.entity.Album;
import br.com.albumfigurinha.api.mapper.AlbumMapper;
import br.com.albumfigurinha.api.service.AlbumService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("api/album")
@AllArgsConstructor
public class AlbumController {

    private final AlbumService albumService;
    private final AlbumMapper albumMapper;

    @PutMapping
    public ResponseEntity<AlbumDTO> updateAlbum(@RequestPart("album_image") MultipartFile file, @RequestPart("album_json") AlbumUpdateDTO albumDto) throws IOException {
        Album album = albumService.updateAlbum(albumDto, file);
        return ResponseEntity.status(HttpStatus.OK).body(albumMapper.toDto(album));
    }

    @GetMapping
    public ResponseEntity<?> getAlbum() {
        return ResponseEntity.status(HttpStatus.OK).body(albumMapper.toDto(albumService.getAlbum()));
    }
}

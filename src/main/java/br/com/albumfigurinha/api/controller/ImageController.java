package br.com.albumfigurinha.api.controller;

import br.com.albumfigurinha.api.dto.ImageDTO;
import br.com.albumfigurinha.api.entity.Image;
import br.com.albumfigurinha.api.service.ImageService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

import java.io.IOException;


@RestController
@RequestMapping("api/image")
@AllArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageDTO> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        ImageDTO image = imageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }

    @GetMapping("/{hashMd5}")
    public ResponseEntity<?> downloadImage(@PathVariable String hashMd5) {
        byte[] imageData = imageService.downloadImage(hashMd5);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(IMAGE_PNG_VALUE))
                .body(imageData);
    }
}

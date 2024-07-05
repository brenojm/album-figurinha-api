package br.com.albumfigurinha.api.controller;

import br.com.albumfigurinha.api.dto.ErrorDTO;
import br.com.albumfigurinha.api.dto.StickerCreateDTO;
import br.com.albumfigurinha.api.dto.StickerDTO;
import br.com.albumfigurinha.api.service.StickerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("api/sticker")
@AllArgsConstructor
public class StickerController {

    private final StickerService stickerService;

    @PostMapping
    public ResponseEntity<StickerDTO> createSticker(@RequestPart("sticker_image") MultipartFile file, @RequestPart("sticker_json") StickerCreateDTO stickerDto) throws IOException {
        StickerDTO sticker = stickerService.createSticker(stickerDto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(sticker);
    }

    @PutMapping("{id}")
    public ResponseEntity<StickerDTO> createSticker(@RequestPart("sticker_image") MultipartFile file, @RequestPart("sticker_json") StickerCreateDTO stickerDto, @PathVariable("id") String stickerId) throws IOException {
        StickerDTO sticker = stickerService.updateSticker(stickerId, stickerDto, file);
        return ResponseEntity.status(HttpStatus.OK).body(sticker);
    }

    @GetMapping
    public ResponseEntity<?> getStickers() {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.getStickers());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getStickerById(@PathVariable("id") String stickerId) {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.getStickerById(stickerId));
    }

    @GetMapping("byMd5/{hashMd5}")
    public ResponseEntity<?> getStickerByMd5(@PathVariable("hashMd5") String stickerTag) {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.getStickerByMd5(stickerTag));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteSticker(@PathVariable("id") String stickerId) {
        stickerService.removeSticker(stickerId);
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorDTO("Sticker deleted with the given ID: { " + stickerId + " }"));
    }

    @PostMapping("collect/{stickerTag}/user/{userId}")
    public ResponseEntity<?> collectSticker(@PathVariable("stickerTag") String stickerTag, @PathVariable("userId") String stickerId) {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.collectSticker(stickerTag, stickerId));
    }

    @GetMapping("collected/{userId}")
    public ResponseEntity<?> getStickerCollected(@PathVariable("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.stickersCollected(userId));
    }

    @GetMapping("collectedAll/{userId}")
    public ResponseEntity<?> getStickerCollectedWithStatus(@PathVariable("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(stickerService.getAllStickersWithCollectedStatus(userId));
    }
}

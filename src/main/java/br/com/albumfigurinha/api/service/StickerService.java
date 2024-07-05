package br.com.albumfigurinha.api.service;

import br.com.albumfigurinha.api.dto.StickerCollectedDTO;
import br.com.albumfigurinha.api.dto.StickerCreateDTO;
import br.com.albumfigurinha.api.dto.StickerDTO;
import br.com.albumfigurinha.api.entity.UserSticker;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StickerService {
    StickerDTO updateSticker(String stickeId,StickerCreateDTO stickerDto, MultipartFile stickerImage) throws IOException;
    StickerDTO createSticker(StickerCreateDTO stickerDto, MultipartFile stickerImage) throws IOException;
    List<StickerDTO> getStickers();
    StickerDTO getStickerById(String stickerId);
    StickerDTO getStickerByMd5(String stickerMd5);
    void removeSticker(String stickerId);
    UserSticker collectSticker(String stickerTag, String userId);
    List<UserSticker> stickersCollected(String userId);
    List<StickerCollectedDTO> getAllStickersWithCollectedStatus(String userId);
}

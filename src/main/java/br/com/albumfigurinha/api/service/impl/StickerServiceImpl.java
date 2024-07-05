package br.com.albumfigurinha.api.service.impl;

import br.com.albumfigurinha.api.dto.StickerCollectedDTO;
import br.com.albumfigurinha.api.dto.StickerCreateDTO;
import br.com.albumfigurinha.api.dto.StickerDTO;
import br.com.albumfigurinha.api.entity.Sticker;
import br.com.albumfigurinha.api.entity.UserSticker;
import br.com.albumfigurinha.api.exception.StickerNotFoundException;
import br.com.albumfigurinha.api.mapper.StickerMapper;
import br.com.albumfigurinha.api.repository.StickerRepository;
import br.com.albumfigurinha.api.repository.UserStickerRepository;
import br.com.albumfigurinha.api.service.ImageService;
import br.com.albumfigurinha.api.service.StickerService;
import br.com.albumfigurinha.api.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StickerServiceImpl implements StickerService {

    private final StickerRepository stickerRepository;
    private final UserStickerRepository userStickerRepository;
    private final ImageService imageService;
    private final UserService userService;
    private final StickerMapper stickerMapper;

    @Override
    public StickerDTO createSticker(StickerCreateDTO stickerDto, MultipartFile stickerImage) throws IOException {
        Sticker sticker = new Sticker();

        sticker.setName(stickerDto.getName());
        sticker.setPage(stickerDto.getPage());
        sticker.setDescription(stickerDto.getDescription());

        var image = imageService.uploadImage(stickerImage);
        sticker.setStickerImage(image);
        sticker.setTag(image.getMd5());

        stickerRepository.save(sticker);
        return stickerMapper.toDto(sticker);
    }

    @Override
    public StickerDTO updateSticker(String stickerId, StickerCreateDTO stickerDto, MultipartFile stickerImage) throws IOException {
        var existingSticker = stickerRepository.findById(stickerId).orElse(null);

        if (existingSticker == null) {
            throw new StickerNotFoundException("Sticker not found with the given ID: { " + stickerId + " }");
        }

        if (!stickerDto.getName().isBlank() && !stickerDto.getName().isEmpty()) {
            existingSticker.setName(stickerDto.getName());
        }
        if (!stickerDto.getDescription().isBlank() && !stickerDto.getDescription().isEmpty()) {
            existingSticker.setDescription(stickerDto.getDescription());
        }
        if (stickerDto.getPage() > 0) {
            existingSticker.setPage(stickerDto.getPage());
        }

        String md5ToDelete = "";
        if (!stickerImage.isEmpty()) {
            if (!imageService.imageExists(existingSticker.getStickerImage().getMd5())) {
                var image = imageService.uploadImage(stickerImage);
                md5ToDelete = existingSticker.getStickerImage().getMd5();
                existingSticker.setStickerImage(image);
                existingSticker.setTag(image.getMd5());
            }
        }

        stickerRepository.save(existingSticker);
        if (!md5ToDelete.isEmpty()) {
            imageService.removeImage(md5ToDelete);
        }


        return stickerMapper.toDto(existingSticker);
    }

    @Override
    public List<StickerDTO> getStickers() {
        List<Sticker> stickers = stickerRepository.findAll();

        return stickers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StickerDTO getStickerById(String stickerId) {
        var existingSticker = stickerRepository.findById(stickerId).orElse(null);
        if (existingSticker == null) {
            throw new StickerNotFoundException("Sticker not found with the given ID: { " + stickerId + " }");
        }

        return stickerMapper.toDto(existingSticker);
    }

    @Override
    public StickerDTO getStickerByMd5(String stickerMd5) {
        var existingSticker = stickerRepository.findByTag(stickerMd5).orElse(null);
        if (existingSticker == null) {
            throw new StickerNotFoundException("Sticker not found with the given Md5: { " + stickerMd5 + " }");
        }
        return stickerMapper.toDto(existingSticker);
    }

    @Override
    public UserSticker collectSticker(String stickerTag, String userId) {
        var existingSticker = stickerRepository.findByTag(stickerTag).orElse(null);
        if (existingSticker == null) {
            throw new StickerNotFoundException("Sticker not found with the given Tag: { " + stickerTag + " }");
        }
        var existingUser = userService.getUserById(userId);
        var existingUserSticker = userStickerRepository.findByUserIdAndStickerTag(userId,stickerTag).orElse(null);
        if (existingUserSticker != null) {
            throw new StickerNotFoundException("Sticker already collected with the given Tag: { " + stickerTag + " }");
        }
        UserSticker userSticker = new UserSticker(UUID.randomUUID().toString(),userId,stickerTag);
        userStickerRepository.save(userSticker);

        return userSticker;
    }
    @Override
    public List<UserSticker> stickersCollected(String userId) {
        userService.getUserById(userId);
        var userStickers = userStickerRepository.findAllByUserId(userId).orElse(null);
        if (userStickers == null) {
            throw new StickerNotFoundException("No stickers have been collected");
        }
        return userStickers;
    }

    @Override
    public List<StickerCollectedDTO> getAllStickersWithCollectedStatus(String userId) {
        userService.getUserById(userId);
        List<Sticker> allStickers = stickerRepository.findAll();
        List<UserSticker> userStickers = userStickerRepository.findAllByUserId(userId).orElse(new ArrayList<>());
        Map<String, Boolean> collectedMap = userStickers.stream()
                .collect(Collectors.toMap(UserSticker::getStickerTag, us -> true));
        List<StickerCollectedDTO> stickerCollectedDTOs = allStickers.stream()
                .map(sticker -> new StickerCollectedDTO(
                        sticker.getId(),
                        sticker.getName(),
                        sticker.getPage(),
                        sticker.getDescription(),
                        sticker.getTag(),
                        collectedMap.getOrDefault(sticker.getTag(), false)
                ))
                .collect(Collectors.toList());
        return stickerCollectedDTOs;
    }

    @Override
    public void removeSticker(String stickerId) {
        var existingSticker = stickerRepository.findById(stickerId).orElse(null);
        if (existingSticker == null) {
            throw new StickerNotFoundException("Sticker not found with the given ID: { " + stickerId + " }");
        }
        stickerRepository.delete(existingSticker);
        imageService.removeImage(existingSticker.getStickerImage().getMd5());
    }

    private StickerDTO convertToDTO(Sticker sticker) {
        return stickerMapper.toDto(sticker);
    }
}

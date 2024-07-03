package br.com.albumfigurinha.api.service.impl;

import br.com.albumfigurinha.api.dto.ImageDTO;
import br.com.albumfigurinha.api.entity.Image;
import br.com.albumfigurinha.api.exception.InvalidImageException;
import br.com.albumfigurinha.api.mapper.ImageMapper;
import br.com.albumfigurinha.api.repository.ImageRepository;
import br.com.albumfigurinha.api.service.ImageService;
import br.com.albumfigurinha.api.utils.ImageUtils;
import lombok.AllArgsConstructor;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.io.InputStream;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    public ImageDTO uploadImage(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new InvalidImageException("The image file is empty");
        }

        if (StringUtils.isEmpty(imageFile.getOriginalFilename())) {
            throw new InvalidImageException("Invalid image file name");
        }

        if (StringUtils.isEmpty(imageFile.getContentType()) || !imageFile.getContentType().startsWith("image")) {
            throw new InvalidImageException("Invalid image file content type");
        }

        Image imageToSave = Image.builder()
                .name(imageFile.getOriginalFilename())
                .type(imageFile.getContentType())
                .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                .build();

        String checksum;
        try (InputStream is = imageFile.getInputStream()) {
            checksum = DigestUtils.md5Hex(is);
        } catch (InvalidImageException e) {
            throw new InvalidImageException("Error calculating MD5 checksum of the image");
        }
        Optional<Image> dbImage = imageRepository.findByMd5(checksum);
        if (dbImage.isPresent()) {
            throw new InvalidImageException("Image already registered");
        }
        imageToSave.setMd5(checksum);
        imageRepository.save(imageToSave);
        return imageMapper.toDto(imageToSave);
    }


    public byte[] downloadImage(String hashMd5) {
        Optional<Image> dbImage = imageRepository.findByMd5(hashMd5);

        return dbImage.map(image -> {
            try {
                return ImageUtils.decompressImage(image.getImageData());
            } catch (DataFormatException | IOException exception) {
                throw new ContextedRuntimeException("Error downloading an image", exception)
                        .addContextValue("Image Md5",  image.getMd5())
                        .addContextValue("Image name", image.getName());
            }
        }).orElse(null);
    }

}

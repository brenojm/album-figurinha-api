package br.com.albumfigurinha.api.service;

import br.com.albumfigurinha.api.dto.ImageDTO;
import br.com.albumfigurinha.api.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageService {

    public ImageDTO uploadImage(MultipartFile imageFile) throws IOException ;

    public byte[] downloadImage(String hashMd5);

}

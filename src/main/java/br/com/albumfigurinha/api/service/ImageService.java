package br.com.albumfigurinha.api.service;

import br.com.albumfigurinha.api.entity.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ImageService {

    public Image uploadImage(MultipartFile imageFile) throws IOException ;

    public byte[] downloadImage(String hashMd5);

    public void removeImage(String hashMd5);

    public boolean imageExists(String md5);
}

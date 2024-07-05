package br.com.albumfigurinha.api.repository;


import br.com.albumfigurinha.api.entity.Album;
import br.com.albumfigurinha.api.entity.Image;
import br.com.albumfigurinha.api.entity.Sticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StickerRepository extends JpaRepository<Sticker, String> {
    Optional<Sticker> findByTag(String tag);
}
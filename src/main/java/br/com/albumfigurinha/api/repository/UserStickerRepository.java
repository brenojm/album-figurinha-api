package br.com.albumfigurinha.api.repository;


import br.com.albumfigurinha.api.entity.Sticker;
import br.com.albumfigurinha.api.entity.UserSticker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserStickerRepository extends JpaRepository<UserSticker, String> {
    Optional<List<UserSticker>> findAllByUserId(String userId);
    Optional<UserSticker> findByUserIdAndStickerTag(String userId, String stickerTag);
}
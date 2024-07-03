package br.com.albumfigurinha.api.repository;

import br.com.albumfigurinha.api.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    Optional<Image> findByName(String name);
    Optional<Image> findByMd5(String md5);
}
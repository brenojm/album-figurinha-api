package br.com.albumfigurinha.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    int pageNumber;
    @OneToOne
    @JoinColumn(name = "images_md5")
    Image albumCover;
}

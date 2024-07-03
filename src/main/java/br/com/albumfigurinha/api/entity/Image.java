package br.com.albumfigurinha.api.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;

import java.sql.Types;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {

//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private String id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Id
    @Column(nullable = false, unique = true)
    private String md5;

    @Lob
    @Column(name = "image_data", columnDefinition = "BLOB")
    private byte[] imageData;
}

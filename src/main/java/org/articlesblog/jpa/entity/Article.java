package org.articlesblog.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;

import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Indexed
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @FullTextField
    @Column(name = "title", length = 250)
    private String title;

    @FullTextField
    @Column(name = "description", length = 250)
    private String description;

    @FullTextField
    @Column(name = "text", columnDefinition = "text")
    private String text;

    @FullTextField
    @Column(name = "author", length = 250)
    private String author;

    @FullTextField
    @Column(name = "label", length = 250)
    private String label;

    @Column(name = "image", length = 500)
    private String image;

    @GenericField
    @Column(name = "date_create", nullable = false, columnDefinition = "timestamp with time zone")
    private LocalDateTime dateCreate;

    @GenericField
    @Column(name = "date_change", columnDefinition = "timestamp with time zone")
    private LocalDateTime dateChange;
}
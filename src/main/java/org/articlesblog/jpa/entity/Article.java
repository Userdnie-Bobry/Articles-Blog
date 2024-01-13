package org.articlesblog.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "articles")
@Data
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", length = 250)
    private String title;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "text", columnDefinition = "text")
    private String text;

    @Column(name = "author", length = 250)
    private String author;

    @Column(name = "label", length = 250)
    private String label;

    @Column(name = "date_create", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateCreate;

    @Column(name = "date_change")
    @Temporal(TemporalType.DATE)
    private Date dateChange;
}
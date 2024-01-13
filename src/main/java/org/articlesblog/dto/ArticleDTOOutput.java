package org.articlesblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTOOutput {
    int id;
    String title;
    String description;
    String author;
    String label;
    String image;
    String dateCreate;
    String dateChange;
}

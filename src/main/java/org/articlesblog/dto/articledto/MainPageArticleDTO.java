package org.articlesblog.dto.articledto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainPageArticleDTO {
    int id;
    String label;
    String author;
    String title;
    String description;
    String dateCreate;
    String image;
}

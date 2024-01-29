package org.articlesblog.dto.articledto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditArticleDTO {
    int id;
    String title;
    String description;
    String text;
    String author;
    String label;
    String image;
}

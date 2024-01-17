package org.articlesblog.dto.articledto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleDTO {
    int id;
    String title;
    String text;
    String author;
    String label;
    String dateCreate;
    String dateChange;
}

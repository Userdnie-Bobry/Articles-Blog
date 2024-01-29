package org.articlesblog.dto.articledto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleDTO {
    int id;
    String title;
    String description;
    String text;
    String author;
    String label;
    MultipartFile multipartFile;
}

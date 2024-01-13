package org.articlesblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTOInput {
    String title;
    String description;
    String author;
    String label;
    MultipartFile multipartFile;
}

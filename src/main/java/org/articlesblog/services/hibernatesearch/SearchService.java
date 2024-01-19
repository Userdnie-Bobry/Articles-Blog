package org.articlesblog.services.hibernatesearch;

import org.articlesblog.dto.articledto.GetAllArticlesDTO;

import java.util.List;

public interface SearchService {
    List<GetAllArticlesDTO> searchBy(String searchQuery);
}

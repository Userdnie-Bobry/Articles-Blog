package org.articlesblog.services.hibernatesearch;

import org.articlesblog.dto.articledto.GetAllArticlesDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface SearchService {
    List<GetAllArticlesDTO> searchByQuery(String searchQuery);
    List<GetAllArticlesDTO> searchByQueryAndStartDate(String searchQuery, LocalDateTime startDate);
    List<GetAllArticlesDTO> searchByQueryAndEndDate(String searchQuery, LocalDateTime endDate);
    List<GetAllArticlesDTO> searchByQueryAndPeriod(String searchQuery, LocalDateTime startDate, LocalDateTime endDate);
}

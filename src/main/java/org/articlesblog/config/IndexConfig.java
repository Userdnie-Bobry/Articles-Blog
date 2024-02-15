package org.articlesblog.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.services.hibernatesearch.IndexService;
import org.articlesblog.services.hibernatesearch.IndexServiceImpl;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IndexConfig implements ApplicationListener<ApplicationReadyEvent> {
    private final IndexService indexService;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        indexService.initiateIndexing();
    }
}
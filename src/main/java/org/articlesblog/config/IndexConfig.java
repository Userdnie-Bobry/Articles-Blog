package org.articlesblog.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.articlesblog.index.IndexInit;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class IndexConfig implements ApplicationListener<ApplicationReadyEvent> {

    private final IndexInit indexInit;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        indexInit.initiateIndexing();
    }
}
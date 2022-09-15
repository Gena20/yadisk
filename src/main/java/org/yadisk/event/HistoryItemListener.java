package org.yadisk.event;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.yadisk.mapper.SystemItemMapper;
import org.yadisk.repository.HistoryItemRepository;

@Component
@AllArgsConstructor
public class HistoryItemListener implements ApplicationListener<HistoryItemEvent> {
    private HistoryItemRepository repository;

    private SystemItemMapper mapper;

    @Override
    public void onApplicationEvent(HistoryItemEvent event) {
        var item = event.getItem();

        repository.saveAndFlush(mapper.toHistoryItem(item));
    }
}

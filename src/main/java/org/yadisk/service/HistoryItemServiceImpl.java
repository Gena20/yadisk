package org.yadisk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.yadisk.entity.HistoryItem;
import org.yadisk.entity.SystemItemType;
import org.yadisk.exception.SystemItemNotFoundException;
import org.yadisk.repository.HistoryItemRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Service
public class HistoryItemServiceImpl implements HistoryItemService {
    private SystemItemService service;

    private HistoryItemRepository repository;

    @Override
    public List<HistoryItem> getFilesByDate(Instant date) {
        return repository.findAllByTypeAndDateBetween(SystemItemType.FILE, date.minus(1, ChronoUnit.DAYS), date);
    }

    @Override
    public List<HistoryItem> getHistoryOfFileByDates(String id, Instant dateFrom, Instant dateTo) throws SystemItemNotFoundException {
        var item = service.getItemById(id);

        return repository.findAllByIdAndDateBetween(item.getId(), dateFrom, dateTo);
    }
}

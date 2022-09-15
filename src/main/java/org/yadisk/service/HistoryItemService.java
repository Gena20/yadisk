package org.yadisk.service;

import org.yadisk.entity.HistoryItem;
import org.yadisk.exception.SystemItemNotFoundException;

import java.time.Instant;
import java.util.List;

public interface HistoryItemService {


    List<HistoryItem> getFilesByDate(Instant date);

    List<HistoryItem> getHistoryOfFileByDates(String id, Instant dateFrom, Instant dateTo) throws SystemItemNotFoundException;
}

package org.yadisk.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.yadisk.dto.SystemItemImport;
import org.yadisk.entity.HistoryItem;
import org.yadisk.entity.SystemItem;
import org.yadisk.entity.SystemItemType;
import org.yadisk.exception.SystemItemInvalidTypeException;

import java.time.Instant;

@AllArgsConstructor
@Component
public class SystemItemMapper {

    private ModelMapper mapper;

    public SystemItem fromImportItem(SystemItemImport importItem, Instant updateDate) {
        var item = mapper.map(importItem, SystemItem.class);
        item.setDate(updateDate);

        return item;
    }

    public SystemItem fromFoundItem(SystemItem foundItem, SystemItemImport importItem, Instant updateDate) throws SystemItemInvalidTypeException {
        if (foundItem.getType() != importItem.getType()) {
            throw new SystemItemInvalidTypeException();
        }

        foundItem.setDate(updateDate);
        if (foundItem.getType() == SystemItemType.FILE) {
            foundItem.setUrl(importItem.getUrl());
            foundItem.setSize(importItem.getSize());
        }

        return foundItem;
    }

    public HistoryItem toHistoryItem(SystemItem item) {
        var historyItem = new HistoryItem();
        historyItem.setId(item.getId());
        historyItem.setType(item.getType());
        historyItem.setParentId(item.getParentId());
        historyItem.setSize(item.getSize());
        historyItem.setDate(item.getDate());
        historyItem.setUrl(item.getUrl());

        return historyItem;
    }
}

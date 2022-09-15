package org.yadisk.service;

import org.yadisk.dto.SystemItemImportRequest;
import org.yadisk.entity.SystemItem;
import org.yadisk.exception.SystemItemInvalidTypeException;
import org.yadisk.exception.SystemItemNotFoundException;

import java.time.Instant;

public interface SystemItemService {
    void importItems(SystemItemImportRequest request) throws SystemItemInvalidTypeException, SystemItemNotFoundException;

    SystemItem getItemById(String id) throws SystemItemNotFoundException;

    void deleteItemById(String id, Instant date) throws SystemItemNotFoundException;
}

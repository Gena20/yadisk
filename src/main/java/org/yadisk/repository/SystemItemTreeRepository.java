package org.yadisk.repository;

import org.yadisk.entity.SystemItem;

import java.util.Map;

public interface SystemItemTreeRepository {
    Map<Integer, SystemItem> getAllParents(SystemItem node);

    void deleteItem(String id);
}

package org.yadisk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.yadisk.dto.SystemItemImport;
import org.yadisk.dto.SystemItemImportRequest;
import org.yadisk.entity.SystemItem;
import org.yadisk.entity.SystemItemType;
import org.yadisk.event.HistoryItemEvent;
import org.yadisk.exception.SystemItemInvalidTypeException;
import org.yadisk.exception.SystemItemNotFoundException;
import org.yadisk.mapper.SystemItemMapper;
import org.yadisk.repository.SystemItemRepository;

import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
@Service
public class SystemItemServiceImpl implements SystemItemService {

    private final SystemItemMapper mapper;

    private final SystemItemRepository repository;

    private final ApplicationEventPublisher publisher;

    Set<SystemItem> itemsToUpdate = new HashSet<>();

    @Override
    public void importItems(SystemItemImportRequest request) throws SystemItemInvalidTypeException, SystemItemNotFoundException {
        itemsToUpdate = new HashSet<>();
        var importItems = request.items();
        var updateDate = request.updateDate();

        var folders = importItems.stream().filter(item -> item.getType() == SystemItemType.FOLDER).toList();
        var files = importItems.stream().filter(item -> item.getType() == SystemItemType.FILE).toList();

        handleImportItems(folders, updateDate);
        handleImportItems(files, updateDate);
        itemsToUpdate.forEach(item -> publisher.publishEvent(new HistoryItemEvent(this, item)));
    }

    @Override
    public SystemItem getItemById(String id) throws SystemItemNotFoundException {
        return repository.findById(id).orElseThrow(SystemItemNotFoundException::new);
    }

    @Override
    public void deleteItemById(String id, Instant date) throws SystemItemNotFoundException {
        var item = getItemById(id);

        updateParents(item, date, -Optional.ofNullable(item.getSize()).orElse(0L));
        repository.deleteItem(id);
    }

    private void handleImportItems(List<SystemItemImport> importItems, Instant updateDate) throws SystemItemInvalidTypeException, SystemItemNotFoundException {
        for (var importItem : importItems) {
            SystemItem item;
            SystemItem foundItem = repository.findById(importItem.getId()).orElse(null);

            if (foundItem == null) {
                item = mapper.fromImportItem(importItem, updateDate);
                setParent(item, importItem.getParentId());
                updateParents(item, updateDate, item.getSize());
            } else {
                item = mapper.fromFoundItem(foundItem, importItem, updateDate);

                var importParentId = importItem.getParentId();
                if (!Objects.equals(item.getParentId(), importParentId)) {
                    changeParent(item, importParentId, updateDate);
                }

                if (Objects.equals(item.getParentId(), importParentId) && item.getType() == SystemItemType.FILE) {
                    updateParents(item, updateDate, importItem.getSize() - item.getSize());
                }
            }

            itemsToUpdate.add(item);
        }

        repository.flush();
    }

    private void setParent(SystemItem item, String parentId) throws SystemItemNotFoundException {
        if (parentId != null) {
            var parent = repository.findById(parentId).orElseThrow(SystemItemNotFoundException::new);
            item.setParent(parent);
        } else {
            item.setParent(null);
        }

        repository.save(item);
    }

    private void updateParents(SystemItem item, Instant date, Long sizeDiff) {
        if (item.getParent() == null) {
            return;
        }

        var parents = repository.getAllParents(item).values();
        parents.forEach(parent -> {
            if (parent != null) {
                parent.setDate(date);
                if (sizeDiff != null) {
                    parent.setSize(Optional.ofNullable(parent.getSize()).orElse(0L) + sizeDiff);
                }
                itemsToUpdate.add(parent);
            }
        });
    }

    private void changeParent(SystemItem item, String importParentId, Instant updateDate) throws SystemItemNotFoundException {
        Long size = item.getSize();

        updateParents(item, updateDate, Objects.isNull(size) ? null : -size);
        setParent(item, importParentId);
        updateParents(item, updateDate, size);
    }
}

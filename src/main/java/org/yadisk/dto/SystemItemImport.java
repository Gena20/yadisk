package org.yadisk.dto;

import org.yadisk.entity.SystemItemType;

import java.util.UUID;

public record SystemItemImport(UUID id, UUID parentId, SystemItemType type, String url, Integer size) {
}

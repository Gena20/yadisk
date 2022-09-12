package org.yadisk.dto;

import org.yadisk.entity.SystemItem;
import org.yadisk.entity.SystemItemType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link SystemItem} entity
 */
public record SystemItemImport(@NotBlank String id, String parentId, @NotNull SystemItemType type, String url,
                               Integer size) implements Serializable {
}

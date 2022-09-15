package org.yadisk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.yadisk.entity.SystemItem;
import org.yadisk.entity.SystemItemType;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the {@link SystemItem} entity
 */
@Data
@AllArgsConstructor
public class SystemItemImport {
    @NotNull
    private String id;
    private String parentId;
    @NotNull
    private SystemItemType type;
    @Size(max = 255)
    private String url;
    @Min(1)
    private Long size;

    @AssertTrue
    public boolean isValid() {
        return type == SystemItemType.FILE && size != null && url != null
                || size == null && url == null;
    }
}

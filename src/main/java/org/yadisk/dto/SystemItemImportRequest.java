package org.yadisk.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

public record SystemItemImportRequest(@NotNull @Valid List<SystemItemImport> items, @NotNull ZonedDateTime updateDate) {
}

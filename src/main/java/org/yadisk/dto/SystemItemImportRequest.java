package org.yadisk.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

public record SystemItemImportRequest(@NotNull @Valid List<SystemItemImport> items, @NotNull Instant updateDate) {
}

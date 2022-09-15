package org.yadisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.yadisk.entity.HistoryItem;
import org.yadisk.entity.SystemItemType;

import java.time.Instant;
import java.util.List;

public interface HistoryItemRepository extends JpaRepository<HistoryItem, String> {
    List<HistoryItem> findAllByTypeAndDateBetween(@NonNull SystemItemType type, @NonNull Instant dateStart, @NonNull Instant dateEnd);

    @Query("select h from HistoryItem h where h.id = :id and h.date >= :dateStart and h.date < :dateEnd")
    List<HistoryItem> findAllByIdAndDateBetween(@Param("id") @NonNull String id, @Param("dateStart") @NonNull Instant dateStart, @Param("dateEnd") @NonNull Instant dateEnd);

}

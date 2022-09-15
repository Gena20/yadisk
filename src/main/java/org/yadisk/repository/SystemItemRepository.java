package org.yadisk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yadisk.entity.SystemItem;

@Repository
public interface SystemItemRepository extends JpaRepository<SystemItem, String>, SystemItemTreeRepository {
}
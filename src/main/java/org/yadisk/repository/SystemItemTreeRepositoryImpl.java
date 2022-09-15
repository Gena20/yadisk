package org.yadisk.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.yadisk.entity.SystemItem;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SystemItemTreeRepositoryImpl implements SystemItemTreeRepository {

    @PersistenceContext
    private final EntityManager em;

    public Map<Integer, SystemItem> getAllParents(SystemItem node) {
        List<Object[]> parents = em.createNamedQuery("getAllParents").setParameter("id", node.getId()).getResultList();
        Map<Integer, SystemItem> result = new HashMap<>();
        parents.forEach(rec -> {
            SystemItem parent = (SystemItem) rec[0];
            Integer level = (Integer) rec[1];
            result.put(level, parent);
        });

        return result;
    }

    @Transactional
    public void deleteItem(String id) {
        em.createNamedQuery("delete").setParameter("id", id).executeUpdate();
    }
}

package com.koopey.api.repository.transaction;

import com.koopey.api.model.dto.JourneyDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Location;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class JourneyTransaction {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteClassificationCategory(Asset asset) {
        entityManager.createNativeQuery("DELETE FROM JOURNEY WHERE asset_id = ?")
          .setParameter(1, asset.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void deleteClassificationItem(Location location) {
        entityManager.createNativeQuery("DELETE FROM JOURNEY WHERE location_id = ?")
          .setParameter(1, location.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void insert(JourneyDto journey) {
        entityManager.createNativeQuery("INSERT INTO JOURNEY (id, asset_id, location_id) VALUES (?,?,?)")
          .setParameter(1, UUID.randomUUID().toString().replace("-", ""))
          .setParameter(2, journey.getAsset_id().replace("-", ""))
          .setParameter(3, journey.getLocation_id().replace("-", ""))
          .executeUpdate();
    }
}

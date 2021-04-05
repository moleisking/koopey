package com.koopey.server.repository;

import com.koopey.server.model.Asset;
import com.koopey.server.model.ClassificationRaw;
import com.koopey.server.model.Tag;

import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ClassificationTransaction {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteClassificationCategory(Asset asset) {
        entityManager.createNativeQuery("DELETE FROM CLASSIFICATION WHERE asset_id = ?")
          .setParameter(1, asset.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void deleteClassificationItem(Tag tag) {
        entityManager.createNativeQuery("DELETE FROM CLASSIFICATION WHERE tag_id = ?")
          .setParameter(1, tag.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void insertClassification(ClassificationRaw classification) {
        entityManager.createNativeQuery("INSERT INTO CLASSIFICATION (id, asset_id, tag_id) VALUES (?,?,?)")
          .setParameter(1, UUID.randomUUID().toString().replace("-", ""))
          .setParameter(2, classification.getAsset_id().replace("-", ""))
          .setParameter(3, classification.getTag_id().replace("-", ""))
          .executeUpdate();
    }
}

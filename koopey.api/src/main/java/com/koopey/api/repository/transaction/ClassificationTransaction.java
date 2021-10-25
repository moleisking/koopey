package com.koopey.api.repository.transaction;

import com.koopey.api.model.dto.ClassificationDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Tag;

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
    public void deleteAsset(Asset asset) {
        entityManager.createNativeQuery("DELETE FROM CLASSIFICATION WHERE asset_id = ?")
          .setParameter(1, asset.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void deleteTag(Tag tag) {
        entityManager.createNativeQuery("DELETE FROM CLASSIFICATION WHERE tag_id = ?")
          .setParameter(1, tag.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void insertClassification(ClassificationDto classification) {
        entityManager.createNativeQuery("INSERT INTO CLASSIFICATION (id, asset_id, tag_id) VALUES (?,?,?)")
          .setParameter(1, UUID.randomUUID().toString().replace("-", ""))
          .setParameter(2, classification.getAsset_id().replace("-", ""))
          .setParameter(3, classification.getTag_id().replace("-", ""))
          .executeUpdate();
    }
}

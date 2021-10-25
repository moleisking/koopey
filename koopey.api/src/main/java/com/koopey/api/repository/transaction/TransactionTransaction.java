package com.koopey.api.repository.transaction;

import com.koopey.api.model.dto.TransactionDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Location;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionTransaction {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteTransaction(Asset asset) {
        entityManager.createNativeQuery("DELETE FROM Transaction WHERE asset_id = ?")
          .setParameter(1, asset.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void deleteTransaction(Location location) {
        entityManager.createNativeQuery("DELETE FROM Transaction WHERE location_id = ?")
          .setParameter(1, location.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void insert(TransactionDto transaction) {
        entityManager.createNativeQuery("INSERT INTO Transaction (id, asset_id, customer_id, destination_id, source_id, refereance, value) VALUES (?,?,?,?,?,?,?)")
          .setParameter(1, UUID.randomUUID().toString().replace("-", ""))
          .setParameter(2, transaction.getAsset_id().replace("-", ""))
          .setParameter(3, transaction.getCustomer_id().replace("-", ""))
          .setParameter(4, transaction.getDestination_id().replace("-", ""))
          .setParameter(5, transaction.getSource_id().replace("-", ""))
          .setParameter(6, transaction.getReferance())
          .setParameter(7, transaction.getValue())
          .executeUpdate();
    }
}

package com.koopey.api.repository.transaction;

import com.koopey.api.model.dto.TransactionDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.User;
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
    public void deleteAsset(Asset asset) {
        entityManager.createNativeQuery("DELETE FROM Transaction WHERE asset_id = ?")
          .setParameter(1, asset.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void deleteBuyer(User buyer) {
        entityManager.createNativeQuery("DELETE FROM Transaction WHERE buyer_id = ?")
          .setParameter(1, buyer.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void deleteDestination(Location destination) {
        entityManager.createNativeQuery("DELETE FROM Transaction WHERE destination_id = ?")
          .setParameter(1, destination.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void deleteSeller(User seller) {
        entityManager.createNativeQuery("DELETE FROM Transaction WHERE seller_id = ?")
          .setParameter(1, seller.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void deleteSource(Location source) {
        entityManager.createNativeQuery("DELETE FROM Transaction WHERE source_id = ?")
          .setParameter(1, source.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void insert(TransactionDto transaction) {
        entityManager.createNativeQuery("INSERT INTO Transaction (id, asset_id, buyer_id, destination_id, seller_id, source_id, refereance, value) VALUES (?,?,?,?,?,?,?,?)")
          .setParameter(1, UUID.randomUUID().toString().replace("-", ""))
          .setParameter(2, transaction.getAssetId().replace("-", ""))
          .setParameter(3, transaction.getBuyerId().replace("-", ""))
          .setParameter(4, transaction.getDestinationId().replace("-", ""))
          .setParameter(5, transaction.getSellerId().replace("-", ""))
          .setParameter(6, transaction.getSourceId().replace("-", ""))
          .setParameter(7, transaction.getReferance())
          .setParameter(8, transaction.getValue())
          .executeUpdate();
    }
}

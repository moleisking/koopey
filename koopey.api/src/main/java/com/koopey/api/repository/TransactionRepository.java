package com.koopey.api.repository;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Transaction;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, UUID> {

        public int countByDestinationIdAndProviderIdAndCustomerIdAndSourceIdAndAssetId(UUID destinationId,
                        UUID providerId, UUID customerId, UUID sourceId, UUID assetId);

        @Transactional
        public void deleteByDestinationId(UUID locationId);

        @Transactional
        public void deleteByProviderId(UUID userId);

        @Transactional
        public void deleteByCustomerId(UUID userId);

        @Transactional
        public void deleteBySourceId(UUID locationId);

        @Transactional
        public void deleteByAssetId(UUID assetId);

        public List<Transaction> findByAssetId(UUID assetId);

        public List<Transaction> findByCustomerId(UUID userId);

        public List<Transaction> findByDestinationId(UUID locationId);

        public List<Transaction> findByProviderId(UUID userId);

        public List<Transaction> findBySourceId(UUID locationId);   

        @Query(nativeQuery = true, value = "SELECT Asset.* FROM Transaction J " + "INNER JOIN Asset A ON A.id = J.asset_id "
        + "WHERE asset_id = :asset_id")
public List<Asset> findAssets(@Param("asset_id") UUID assetId);

@Query(nativeQuery = true, value = "SELECT User.* FROM Transaction J "
+ "INNER JOIN User U ON U.id = J.customer_id " + "WHERE U.id = :customer_id")
public List<User> findCustomers(@Param("customer_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT Location.* FROM Transaction J"
                        + "INNER JOIN Location L ON L.id = J.destination_id " + "WHERE destination_id = :location_id")
        public List<Location> findDestinations(@Param("location_id") UUID locationId);

        @Query(nativeQuery = true, value = "SELECT User.* FROM Transaction J " + "INNER JOIN User U ON  U.id = J.provider_id "
        + "WHERE U.id = :provider_id")
public List<User> findProviders(@Param("provider_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT Location.* FROM Transaction J"
                        + "INNER JOIN Location L ON L.id = J.source_id " + "WHERE location_id = :location_id")
        public List<Location> findSources(@Param("location_id") UUID locationId);     
      

}

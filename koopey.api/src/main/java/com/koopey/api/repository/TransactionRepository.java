package com.koopey.api.repository;

import com.koopey.api.model.entity.Asset;
import com.koopey.api.model.entity.Transaction;
import com.koopey.api.model.entity.Location;
import com.koopey.api.model.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, UUID> {

        public long countByDestinationIdAndSellerIdAndBuyerIdAndSourceIdAndAssetId(UUID destinationId, UUID sellerId,
                        UUID buyerId, UUID sourceId, UUID assetId);

        public long countByIdAndBuyerId(UUID id, UUID buyerId);

        public long countByIdAndSellerId(UUID id, UUID sellerId);

        @Transactional
        public void deleteByDestinationId(UUID locationId);

        @Transactional
        public void deleteBySellerId(UUID userId);

        @Transactional
        public void deleteByBuyerId(UUID userId);

        @Transactional
        public void deleteBySourceId(UUID locationId);

        @Transactional
        public void deleteByAssetId(UUID assetId);

        public List<Transaction> findByAssetId(UUID assetId);

        public List<Transaction> findByBuyerId(UUID userId);

        public List<Transaction> findByDestinationId(UUID locationId);

        public List<Transaction> findBySellerId(UUID userId);

        public List<Transaction> findBySourceId(UUID locationId);

        @Query(nativeQuery = true, value = "SELECT Asset.* FROM Transaction T "
                        + "INNER JOIN Asset A ON A.id = T.asset_id " + "WHERE asset_id = :asset_id")
        public List<Asset> findAssets(@Param("asset_id") UUID assetId);

        @Query(nativeQuery = true, value = "SELECT User.* FROM Transaction T "
                        + "INNER JOIN User U ON U.id = T.buyer_id " + "WHERE U.id = :buyer_id")
        public List<User> findBuyers(@Param("buyer_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT Location.* FROM Transaction T"
                        + "INNER JOIN Location L ON L.id = T.destination_id " + "WHERE destination_id = :location_id")
        public List<Location> findDestinations(@Param("location_id") UUID locationId);

        @Query(nativeQuery = true, value = "SELECT User.* FROM Transaction T "
                        + "INNER JOIN User U ON  U.id = T.seller_id " + "WHERE U.id = :seller_id")
        public List<User> findSellers(@Param("seller_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT Location.* FROM Transaction T"
                        + "INNER JOIN Location L ON L.id = T.source_id " + "WHERE source_id = :location_id")
        public List<Location> findSources(@Param("location_id") UUID locationId);

        @Query(nativeQuery = true, value = "SELECT Location.* FROM Transaction T"
                        + "INNER JOIN Location L ON L.id = T.source_id " + "WHERE source_id = :location_id")
        public Page<List<Location>> findSources(@Param("location_id") UUID locationId, Pageable pagable);

}

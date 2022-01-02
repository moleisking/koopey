package com.koopey.api.repository;

import com.koopey.api.model.entity.Location;
import com.koopey.api.repository.base.AuditRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends AuditRepository<Location, UUID> {

        public Optional<Location> findById(@Param("id") UUID id);

        @Query(nativeQuery = true, value = "SELECT L.* , "
                        + " (6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(lat )))) AS distance"
                        + " FROM Location L" + " HAVING distance < :range ORDER BY distance LIMIT 0, 20")
        public List<Location> findByAreaAsKilometer(@Param("latitude") BigDecimal latitude,
                        @Param("longitude") BigDecimal longitude, @Param("range") Integer radius);

        @Query(nativeQuery = true, value = "SELECT L.* , "
                        + " (6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(lat )))) AS distance"
                        + " FROM Location L" + " HAVING distance < :range ORDER BY distance LIMIT 0, 20")
        public Page<List<Location>> findByAreaAsKilometer(@Param("latitude") BigDecimal latitude,
                        @Param("longitude") BigDecimal longitude, @Param("range") Integer radius, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT L.* , "
                        + " (3959 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(lat )))) AS distance"
                        + " FROM Location L" + " HAVING distance < :range ORDER BY distance LIMIT 0, 20")
        public List<Location> findByAreaAsMiles(@Param("latitude") BigDecimal latitude,
                        @Param("longitude") BigDecimal longitude, @Param("range") Integer radius);

        @Query(nativeQuery = true, value = "SELECT L.* , "
                        + " (3959 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(lat )))) AS distance"
                        + " FROM Location L" + " HAVING distance < :range ORDER BY distance LIMIT 0, 20")
        public Page<List<Location>> findByAreaAsMiles(@Param("latitude") BigDecimal latitude,
                        @Param("longitude") BigDecimal longitude, @Param("range") Integer radius, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.destination_id " + "WHERE T.asset_id = :asset_id")
        public List<Location> findByAssetAndDestination(@Param("asset_id") UUID assetId);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.destination_id " + "WHERE T.asset_id = :asset_id")
        public Page<List<Location>> findByAssetAndDestination(@Param("asset_id") UUID assetId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.source_id " + "WHERE T.asset_id = :asset_id")
        public List<Location> findByAssetAndSource(@Param("asset_id") UUID assetId);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.source_id " + "WHERE T.asset_id = :asset_id")
        public Page<List<Location>> findByAssetAndSource(@Param("asset_id") UUID assetId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.destination_id " + "WHERE T.buyer_id = :buyer_id")
        public List<Location> findByBuyerAndDestination(@Param("buyer_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.destination_id " + "WHERE T.buyer_id = :buyer_id")
        public Page<List<Location>> findByBuyerAndDestination(@Param("buyer_id") UUID userId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.source_id " + "WHERE T.buyer_id = :buyer_id")
        public List<Location> findByBuyerAndSource(@Param("buyer_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.source_id " + "WHERE T.buyer_id = :buyer_id")
        public Page<List<Location>> findByBuyerAndSource(@Param("buyer_id") UUID userId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T"
                        + "INNER JOIN Location L ON L.id = T.destination_id " + "WHERE T.destination_id = :location_id")
        public List<Location> findByDestination(@Param("location_id") UUID locationId);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T"
                        + "INNER JOIN Location L ON L.id = T.destination_id " + "WHERE T.destination_id = :location_id")
        public Page<List<Location>> findByDestination(@Param("location_id") UUID locationId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.destination_id " + "WHERE T.seller_id = :seller_id")
        public List<Location> findByDestinationAndSeller(@Param("seller_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.destination_id " + "WHERE T.seller_id = :seller_id")
        public Page<List<Location>> findByDestinationAndSeller(@Param("seller_id") UUID userId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.source_id " + "WHERE T.seller_id = :seller_id")
        public List<Location> findBySellerAndSource(@Param("seller_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T "
                        + "INNER JOIN Location L ON  L.id = T.source_id " + "WHERE T.seller_id = :seller_id")
        public Page<List<Location>> findBySellerAndSource(@Param("seller_id") UUID userId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T"
                        + "INNER JOIN Location L ON L.id = T.source_id " + "WHERE T.source_id = :source_id")
        public List<Location> findBySource(@Param("source_id") UUID locationId);

        @Query(nativeQuery = true, value = "SELECT L.* FROM Transaction T"
                        + "INNER JOIN Location L ON L.id = T.source_id " + "WHERE source_id = :source_id")
        public Page<List<Location>> findBySource(@Param("source_id") UUID locationId, Pageable pagable);
}

package com.koopey.api.repository;

import com.koopey.api.model.dto.SearchDto;
import com.koopey.api.model.entity.Asset;
import com.koopey.api.repository.base.BaseRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends BaseRepository<Asset, UUID> {

        Page<Asset> findByName(SearchDto search, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id "
                        + "WHERE T.buyer_id = :buyer_id")
        public List<Asset> findByBuyer(@Param("buyer_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id " + "WHERE T.buyer_id = :buyer_id")
        public Page<List<Asset>> findByBuyer(@Param("buyer_id") UUID userId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id "
                        + "WHERE T.buyer_id = :user_id Or T.seller_id = :user_id")
        public List<Asset> findByBuyerOrSeller(@Param("user_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id "
                        + "WHERE T.buyer_id = :user_id Or T.seller_id = :user_id")
        public Page<List<Asset>> findByBuyerOrSeller(@Param("user_id") UUID userId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id " + "WHERE T.destination_id = :destination_id")
        public List<Asset> findByDestination(@Param("destination_id") UUID locationId);

        @Query(nativeQuery = true, value = "SELECT A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id " + "WHERE T.destination_id = :destination_id")
        public Page<List<Asset>> findByDestination(@Param("destination_id") UUID locationId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id "
                        + "WHERE T.seller_id = :seller_id")
        public List<Asset> findBySeller(@Param("seller_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id " + "WHERE T.seller_id = :seller_id")
        public Page<List<Asset>> findBySeller(@Param("seller_id") UUID userId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id " + "WHERE T.source_id = :source_id")
        public List<Asset> findBySource(@Param("source_id") UUID locationId);

        @Query(nativeQuery = true, value = "SELECT A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id " + "WHERE T.source_id = :source_id")
        public Page<List<Asset>> findBySource(@Param("source_id") UUID locationId, Pageable pagable);

        @Query(nativeQuery = true, value = "SELECT TOP 100 A.* FROM Transaction T "
                        + "INNER JOIN Asset A ON  A.id = T.asset_id " + "WHERE T.buyer_id != :user_id "
                        + "&& T.seller_id != :user_id && T.createTimeStamp >=:start")
        public List<Asset> findDefault(@Param("user_id") UUID userId, @Param("start") Long start);

}
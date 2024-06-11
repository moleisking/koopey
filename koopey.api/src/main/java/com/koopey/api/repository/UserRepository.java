package com.koopey.api.repository;

import com.koopey.api.model.entity.User;
import com.koopey.api.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

        public void deleteById(@NotNull @Param("id") UUID id);

        public boolean existsById(@NotNull @Param("id") UUID id);

        public Boolean existsByAlias(@Param("alias") String alias);

        public Boolean existsByEmailOrIdOrMobile(String email, UUID id, String mobile);

        @Query("SELECT u FROM User u WHERE u.name=:name")
        public List<User> findByName(@Param("name") String name);

        public Optional<User> findByAlias(@Param("alias") String alias);

        public Optional<User> findByEmail(@Param("email") String email);

        @Query("SELECT u.alias FROM User u WHERE u.email=:email")
        public String findAliasByEmail(@Param("email") String email);
        
        public User findByAliasOrEmail(@Param("alias") String alias, @Param("email") String email);

        @Query("SELECT u.email FROM User u WHERE u.alias=:alias")
        public String findEmailByAlias(@Param("alias") String alias);

        @Query(nativeQuery = true, value = "SELECT U.* FROM Transaction T " + "INNER JOIN User U ON U.id = T.buyer_id "
                        + "WHERE U.id = :buyer_id")
        public List<User> findBuyers(@Param("buyer_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT U.* FROM Transaction T "
                        + "INNER JOIN User U ON  U.id = T.seller_id " + "WHERE U.id = :seller_id")
        public List<User> findSellers(@Param("seller_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT U.* FROM Conversation C " + "INNER JOIN User U ON  U.id = C.user_id "
                        + "WHERE U.id = :user_id")
        public List<User> findListeners(@Param("user_id") UUID userId);

        public Optional<User> findById(@NotNull @Param("id") UUID id);

        public Optional<User> findByEmailAndId( @NotNull @Param("email") String email, @NotNull @Param("id") UUID id);

        public Optional<User> findByAliasAndId( @NotNull @Param("alias") String email, @NotNull @Param("id") UUID id);

        public Optional<User> findByGuid(@NotNull @Param("guid") UUID guid);

        public User saveAndFlush(@NotNull @Param("user") User user);

}
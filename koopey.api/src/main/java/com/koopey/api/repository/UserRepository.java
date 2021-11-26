package com.koopey.api.repository;

import com.koopey.api.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

        public void deleteById(@Param("id") UUID id);

        public boolean existsById(@Param("id") UUID id);

        public Boolean existsByAlias(@Param("alias") String alias);

        public Boolean existsByEmailOrMobile(@Param("email") String email, @Param("mobile") String mobile);

        @Query("SELECT u FROM User u WHERE u.name=:name")
        public List<User> findByName(@Param("name") String name);

        public User findByAlias(@Param("alias") String alias);

        public User findByEmail(@Param("email") String email);

        public User findByAliasOrEmail(@Param("alias") String alias, @Param("email") String email);

        @Query(nativeQuery = true, value = "SELECT U.* FROM Transaction T " + "INNER JOIN User U ON U.id = T.buyer_id "
                        + "WHERE U.id = :buyer_id")
        public List<User> findBuyers(@Param("buyer_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT U.* FROM Transaction T "
                        + "INNER JOIN User U ON  U.id = T.seller_id " + "WHERE U.id = :seller_id")
        public List<User> findSellers(@Param("seller_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT U.* FROM Conversation C " + "INNER JOIN User U ON  U.id = C.user_id "
                        + "WHERE U.id = :user_id")
        public List<User> findListeners(@Param("user_id") UUID userId);

        public Optional<User> findById(@Param("id") UUID id);

        public User saveAndFlush(@Param("user") User user);

}
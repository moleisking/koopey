package com.koopey.api.repository;

import com.koopey.api.model.entity.Game;
import com.koopey.api.repository.base.BaseRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends BaseRepository<Game, UUID> {

    @Query(nativeQuery = true, value = "SELECT Count(g.Id) FROM Game g "
            + "WHERE g.blackId = :user_id OR g.blueId = :user_id OR g.greyId = :user_id OR g.greenId = :user_id " +
            "OR g.redId = :user_id OR g.whiteId = :user_id")
    public long countByPlayer(@Param("user_id") UUID userId);

    @Query(nativeQuery = true, value = "SELECT g.* FROM Game g "
             + "WHERE g.blackId = :user_id OR g.blueId = :user_id OR g.greyId = :user_id OR g.greenId = :user_id " +
            "OR g.redId = :user_id OR g.whiteId = :user_id")
    public List<Game> findByPlayer(@Param("user_id") UUID userId);

}

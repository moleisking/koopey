package com.koopey.api.repository;

import com.koopey.api.model.entity.Competition;
import com.koopey.api.model.entity.Game;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends BaseRepository<Competition, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM Competition C " + "WHERE user_id = :user_id "
            + "AND game_id = :game_id")
    public int findDuplicate(@Param("user_id") UUID userId, @Param("game_id") UUID gameId);

    public List<Competition> findByGameId(@Param("game_id") UUID gameId);

    public List<Competition> findByUserId(@Param("user_id") UUID userId);

    @Query(nativeQuery = true, value = "SELECT Game.* FROM Competition C "
            + "INNER JOIN Game G ON C.game_id = G.game_id " + "WHERE C.user_id = :user_id")
    public List<Game> findMyGames(@Param("user_id") UUID userId);

    @Query(nativeQuery = true, value = "SELECT Game.* FROM Competition C "
            + "INNER JOIN Game G ON C.game_id = G.game_id " + "WHERE G.game_id = :game_id")
    public List<Game> findPlayers(@Param("game_id") UUID gameId);

}

package com.koopey.api.repository;

import com.koopey.api.model.entity.Competition;
import com.koopey.api.model.entity.Game;
import com.koopey.api.model.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CompetitionRepository extends BaseRepository<Competition, UUID> {

        public int countByGameIdAndUserId(UUID gameId, UUID userId);

        @Transactional
        public void deleteByGameId(UUID locationId);

        @Transactional
        public void deleteByUserId(UUID userId);

        public List<Competition> findByGameId(UUID gameId);

        public List<Competition> findByUserId(UUID userId);

        @Query(nativeQuery = true, value = "SELECT Game.* FROM Competition C "
                        + "INNER JOIN Game G ON C.game_id = G.game_id " + "WHERE C.user_id = :user_id")
        public List<Game> findGames(@Param("user_id") UUID userId);

        @Query(nativeQuery = true, value = "SELECT User.* FROM Competition C "
                        + "INNER JOIN Game G ON C.game_id = G.game_id " + "WHERE G.game_id = :game_id")
        public List<User> findPlayers(@Param("game_id") UUID gameId);

}

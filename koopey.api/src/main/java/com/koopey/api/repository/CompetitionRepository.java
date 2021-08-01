package com.koopey.api.repository;

import com.koopey.api.model.entity.Competition;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetitionRepository extends CrudRepository<Competition, UUID> {

    @Query(nativeQuery = true, value = "SELECT * FROM Competition C WHERE user_id = :user_id AND game_id = :game_id ")
    public int findDuplicate(@Param("user_id")UUID userId, @Param("game_id")UUID gameId);
}

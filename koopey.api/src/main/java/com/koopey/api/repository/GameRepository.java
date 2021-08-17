package com.koopey.api.repository;

import com.koopey.api.model.entity.Game;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends BaseRepository<Game, UUID> {}

package com.koopey.server.repository;

import com.koopey.server.model.Game;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {}

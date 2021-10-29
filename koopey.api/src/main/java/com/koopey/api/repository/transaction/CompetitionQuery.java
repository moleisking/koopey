package com.koopey.api.repository.transaction;

import com.koopey.api.model.dto.CompetitionDto;
import com.koopey.api.model.entity.Game;
import com.koopey.api.model.entity.User;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class CompetitionQuery {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteUser(User user) {
        entityManager.createNativeQuery("DELETE FROM COMPETITION WHERE user_id = ?")
          .setParameter(1, user.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void deleteGame(Game game) {
        entityManager.createNativeQuery("DELETE FROM COMPETITION WHERE game_id = ?")
          .setParameter(1, game.getId().toString().replace("-", ""))       
          .executeUpdate();
    }

    @Transactional
    public void insert(CompetitionDto competition) {
        entityManager.createNativeQuery("INSERT INTO COMPETITION (id, user_id, game_id) VALUES (?,?,?)")
          .setParameter(1, UUID.randomUUID().toString().replace("-", ""))
          .setParameter(2, competition.getUserId().replace("-", ""))
          .setParameter(3, competition.getGameId().replace("-", ""))
          .executeUpdate();
    }
}

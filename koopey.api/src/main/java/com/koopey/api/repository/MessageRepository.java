package com.koopey.api.repository;

import com.koopey.api.model.entity.Message;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends BaseRepository<Message, UUID>{
}
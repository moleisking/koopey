package com.koopey.server.data;

import java.util.List;
import java.util.Optional;

import com.koopey.server.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.name=:name")
    public List<User> findByName(@Param("name") String name);

    public User findByAlias(@Param("alias") String alias);

    public Optional<User> findById(@Param("id") String id);

}
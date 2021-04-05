package com.koopey.server.repository;

import com.koopey.server.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.name=:name")
    public List<User> findByName(@Param("name") String name);

    public User findByUsername(@Param("username") String username);

    public Boolean existsByUsername( @Param("username") String username);

    public Boolean existsByEmailOrMobile( @Param("email") String email, @Param("mobile") String mobile);
  
    public Optional<User> findById(@Param("id") String id);

}
package com.koopey.api.repository;

import com.koopey.api.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.name=:name")
    public List<User> findByName(@Param("name") String name);

    public User findByUsername(@Param("alias") String alias);
    
    public User findByEmail( @Param("email") String email);

    public User findByUsernameOrEmail(@Param("alias") String alias, @Param("email") String email);

    public void deleteById( @Param("id") UUID id);

    public boolean existsById( @Param("id") UUID id);

    public Boolean existsByUsername( @Param("alias") String alias);

    public Boolean existsByEmailOrMobile( @Param("email") String email, @Param("mobile") String mobile);
  
    public Optional<User> findById(@Param("id") UUID id);

    public User saveAndFlush(@Param("user") User user);
    

}
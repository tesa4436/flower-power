package com.flowerpower.data.repository;

import com.flowerpower.security.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("from User u where u.username in ?1")
    User findByUsername(String username);
}

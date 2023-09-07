package com.glaze.flow.repositories;

import java.util.Optional;

import com.glaze.flow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("""
    select u from User u
    inner join UserDetails us on u.id = us.id
    where u.username = ?1 or u.email = ?2
    """)
    Optional<User> findByUsernameOrEmail(String username, String email);
}

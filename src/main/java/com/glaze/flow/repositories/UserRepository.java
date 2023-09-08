package com.glaze.flow.repositories;

import java.util.Optional;

import com.glaze.flow.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("""
    select user, details from User user
    inner join UserDetails details on user.id = details.id
    where user.username = ?1 or user.email = ?1
    """)
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
}

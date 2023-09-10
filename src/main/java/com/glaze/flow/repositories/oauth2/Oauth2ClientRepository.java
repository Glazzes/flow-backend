package com.glaze.flow.repositories.oauth2;

import java.util.Optional;

import com.glaze.flow.entities.oauth2.Oauth2Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Oauth2ClientRepository extends JpaRepository<Oauth2Client, String> {
    @Query("select c from Oauth2Client c where c.clientId = :clientId")
    Optional<Oauth2Client> findByClientId(String clientId);
}

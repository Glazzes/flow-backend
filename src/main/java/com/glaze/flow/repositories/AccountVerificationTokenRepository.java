package com.glaze.flow.repositories;

import com.glaze.flow.entities.AccountVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountVerificationTokenRepository extends JpaRepository<AccountVerificationToken, Long> {}

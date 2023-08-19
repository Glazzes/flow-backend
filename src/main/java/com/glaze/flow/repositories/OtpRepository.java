package com.glaze.flow.repositories;

import com.glaze.flow.entities.Otp;
import com.glaze.flow.enums.OTPType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    @Query("select o from Otp o where o.id = :userId and o.token = :token and o.type = :type")
    Optional<Otp> findByIdAndTokenAndType(Long id, String token, OTPType type);

}

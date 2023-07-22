package com.glaze.flow.repositories;

import com.glaze.flow.entities.OTP;
import com.glaze.flow.enums.OTPType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {

    @Query("select o from OTP o where o.user.id = :userId and o.token = :token and o.type = :type")
    Optional<OTP> findByUserIdAndTokenAndType(Long userId, String token, OTPType type);

}

package com.glaze.flow.services;

import com.glaze.flow.entities.User;
import com.glaze.flow.entities.UserDetails;
import com.glaze.flow.repositories.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public void save(User user) {
        UserDetails userDetails = UserDetails.getSignUpInstance();
        userDetails.setUser(user);

        userDetailsRepository.save(userDetails);
    }

}

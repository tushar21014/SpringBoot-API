package com.firstAPI.firstAPI.Services;

import com.firstAPI.firstAPI.Entity.UserEntity;
import com.firstAPI.firstAPI.Repo.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private com.firstAPI.firstAPI.Repo.userRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByUsername(username);

                if(user != null)
                {
//                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
                    UserDetails userDetails = User.builder()
                            .username(user.getUsername())
                            .password(user.getPassword())
                            .roles(user.getRoles().toArray(new String[0]))
                            .build();

                    return userDetails;
                }

                throw new UsernameNotFoundException("Username not found: " + username);
    }
}

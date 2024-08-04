package com.firstAPI.firstAPI.Services;

import com.firstAPI.firstAPI.Entity.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class userService {

    @Autowired
    private com.firstAPI.firstAPI.Repo.userRepo repo;


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    public void saveNewUserr(UserEntity user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        repo.save(user);
    }

    public void saveUser(UserEntity user)
    {
        repo.save(user);
    }

    public void saveAdminUser(UserEntity user)
    {
        user.setRoles(Arrays.asList("ADMIN"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);

    }

    public List<UserEntity> getAllUsers(){
        return new ArrayList<>(repo.findAll());
    }

    public Optional<UserEntity> getSpecificUser(ObjectId id)
    {
        return repo.findById(id);
    }
    public Optional<UserEntity> getUserbyusername(String username)
    {
        return Optional.ofNullable(repo.findByUsername(username));
    }

    public boolean deleteUser(ObjectId id)
    {
        repo.deleteById(id);
        return true;
    }
}

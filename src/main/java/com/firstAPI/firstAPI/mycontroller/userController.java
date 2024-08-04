package com.firstAPI.firstAPI.mycontroller;

import com.firstAPI.firstAPI.Entity.UserEntity;
import com.firstAPI.firstAPI.Repo.userRepo;
import com.firstAPI.firstAPI.Services.EmaiService;
import com.firstAPI.firstAPI.Services.UserDetailServiceImpl;
import com.firstAPI.firstAPI.Services.userService;
import com.firstAPI.firstAPI.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userService userService;

    @Autowired
    private userRepo userRepo;

    @Autowired
    private EmaiService emailService;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

//    private static final Logger logger = LoggerFactory.getLogger(userController.class);

    @GetMapping("getUsers")
    public List<UserEntity> getAll() {
        return userService.getAllUsers();
    }

    @PostMapping("createUser")
    public ResponseEntity<UserEntity> createEntry(@RequestBody UserEntity user)
    {
        try{
            userService.saveNewUserr(user);
//            emailService.sendEmail(user.getUsername(),"welcome to my journal App","You have successfully created an account");

//        j.put(myEntry.getId(),myEntry);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e)
        {
            log.error("There is an error while creating an user ", e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public UserEntity updateEntry(@RequestBody UserEntity user)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserEntity old = userService.getUserbyusername(username).orElse(null);
        if(old != null)
        {
            old.setUsername(user.getUsername() != null && !user.getUsername().equals("") ? user.getUsername() : old.getUsername());
            old.setPassword(user.getPassword() != null && !user.getPassword().equals("")? user.getPassword() : old.getPassword());
        }

        userService.saveNewUserr(old);

        return old;
    }


    @DeleteMapping
    public ResponseEntity<?> deleteUser()
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            userRepo.deleteByUsername(authentication.getName());
            return new ResponseEntity("User Deleted Successfully", HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody UserEntity user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String token = jwtUtils.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(token, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error while logging in user " , e);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}

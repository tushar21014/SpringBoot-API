package com.firstAPI.firstAPI.mycontroller;

import com.firstAPI.firstAPI.Entity.UserEntity;
import com.firstAPI.firstAPI.Services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private userService userService;

    @GetMapping("/getUsers")
    public ResponseEntity<List<UserEntity>> getAll() {
        try {
            System.out.println("GET ALL USERS ARE HIT" );
            return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("ERROR WHILE GETTING USERS");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody UserEntity user)
    {
        try {
            System.out.println("I am the username " + user.getUsername());
            userService.saveAdminUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (Exception e)
        {
            e.printStackTrace();
//            System.out.println("Error while creatin admin" + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}

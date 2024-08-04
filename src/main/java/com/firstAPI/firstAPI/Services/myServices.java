package com.firstAPI.firstAPI.Services;

import com.firstAPI.firstAPI.Entity.JournalEntry;
import com.firstAPI.firstAPI.Entity.UserEntity;
import com.firstAPI.firstAPI.Repo.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class myServices {

    @Autowired
    private JournalEntryRepo repo;

    @Autowired
    private userService userService;

    public void saveUpdatedEntry(JournalEntry myEntry){
        try {
            repo.save(myEntry);
        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error while saving the updated entry in service " + e);
        }
    }

    public void saveEntry(JournalEntry myEntry, String username) {
        try {
            Optional<UserEntity> userOptional = userService.getUserbyusername(username);

            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();
                JournalEntry saved = repo.save(myEntry);
                user.getJournalEntries().add(saved);
                userService.saveUser(user);
            } else {
                System.err.println("User not found: " + username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<JournalEntry> getAllEntries(){

        return new ArrayList<>(repo.findAll());
    }

    public Optional<JournalEntry> getSpecificEntry(ObjectId id)
    {
        return repo.findById(id);
    }

    public boolean deleteEntry(ObjectId id, String username) {
        try {
            Optional<UserEntity> userOptional = userService.getUserbyusername(username);

            if (userOptional.isPresent()) {
                UserEntity user = userOptional.get();

                boolean removed = user.getJournalEntries().removeIf(entry -> entry.getId().equals(id.toString()));

                if (removed) {
                    userService.saveUser(user);
                    repo.deleteById(id);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}

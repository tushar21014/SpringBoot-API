package com.firstAPI.firstAPI.mycontroller;

import com.firstAPI.firstAPI.Entity.JournalEntry;
import com.firstAPI.firstAPI.Entity.UserEntity;
import com.firstAPI.firstAPI.Services.myServices;
import com.firstAPI.firstAPI.Services.userService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class controller {


    @Autowired
    private myServices myService;

    @Autowired
    private com.firstAPI.firstAPI.Services.userService userService;

    private Map<Long, JournalEntry> j = new HashMap<>();


    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<UserEntity> user = userService.getUserbyusername(username);
//            return new ArrayList<>(user.get().getJournalEntries());
            return ResponseEntity.ok(new ArrayList<>(user.get().getJournalEntries()));


        }catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry)
    {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            myService.saveEntry(myEntry,username);
//        j.put(myEntry.getId(),myEntry);
        return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("specificEntry/{id}")
    public ResponseEntity<Optional<JournalEntry>> specificEntry(@PathVariable ObjectId id)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            Optional<JournalEntry> journalEntry = myService.getSpecificEntry(id);
            Optional<UserEntity> user = userService.getUserbyusername(username);
            List<JournalEntry> collect = user.get().getJournalEntries().stream().filter(x-> x.getId().equals(id)).collect(Collectors.toList());
            if(!collect.isEmpty())
            {
                Optional<JournalEntry> myEntry = myService.getSpecificEntry(id);
                if(myEntry.isPresent()){
                    return new ResponseEntity<>(journalEntry, HttpStatus.OK);
                }
            }

            return new ResponseEntity<>(journalEntry, HttpStatus.NOT_FOUND);
        } catch (Exception e){
            e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeEntry(@PathVariable ObjectId id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            boolean deleted = myService.deleteEntry(id, username);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateEntry/{id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry myEntry)
    {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Optional<UserEntity> user = userService.getUserbyusername(username);
            List<JournalEntry> collect = user.get().getJournalEntries().stream().filter(x-> x.getId().equals(id.toString())).collect(Collectors.toList());

            if(!collect.isEmpty())
            {
                JournalEntry old = myService.getSpecificEntry(id).orElse(null);
                if(old != null)
                {
                    old.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : old.getContent());
                    old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("")? myEntry.getTitle() : old.getTitle());
                    myService.saveUpdatedEntry(old);
                    return new ResponseEntity<>(old, HttpStatus.OK);
                }

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error while updating the journal " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }




    }
}

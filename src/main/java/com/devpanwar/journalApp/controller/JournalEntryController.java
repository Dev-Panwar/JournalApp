package com.devpanwar.journalApp.controller;


import com.devpanwar.journalApp.entity.JournalEntry;
import com.devpanwar.journalApp.entity.User;
import com.devpanwar.journalApp.service.JournalEntryService;
import com.devpanwar.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal APIs", description = "get, create, update and delete journal entries")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Create journal entry", description = "Create journal entry with title and content")
    @PostMapping("/create")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry theEntry){
        try {
            //authentication username and password
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        getting username after authentication is successful
            String userName = authentication.getName();
            journalEntryService.saveEntry(theEntry,userName);
            return new ResponseEntity<>(theEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get all journal entries", description = "Get all journal entries")
    @GetMapping("/findAll")
    public ResponseEntity<?> getAll(){
       List<JournalEntry> all=journalEntryService.findAll();
       if (all!=null && !all.isEmpty()){
           return new ResponseEntity<>(all,HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get all journal entries of user", description = "Get all journal entries of user")
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        //        authentication username and password
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        getting username after authentication is successful
        String userName = authentication.getName();
        User user=userService.findByUserName(userName);
        List<JournalEntry> all=user.getJournalEntries();
        if (all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Get journal entry by id", description = "Get journal entry by id")
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user=userService.findByUserName(userName);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
//        means given journal id belongs to user
        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "Delete journal entry by id", description = "Delete journal entry by id")
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed=journalEntryService.deleteById(myId,userName);
        if (removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "Update journal entry by id", description = "Update journal entry by id")
    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user=userService.findByUserName(userName);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
//        means given journal id belongs to user
        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()){
                JournalEntry old=journalEntry.get();
                // jo bhi humko body me change karne diya gaya hai keval wohi cheej update karenge remaining vesa hi rahega
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}

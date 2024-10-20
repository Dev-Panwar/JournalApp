package com.devpanwar.journalApp.controller;


import com.devpanwar.journalApp.cache.AppCache;
import com.devpanwar.journalApp.entity.User;
import com.devpanwar.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userService.findAll();
        if (!users.isEmpty()){
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Users found",HttpStatus.NOT_FOUND);
    }


    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createEntry(@RequestBody User theEntry) {
        if (theEntry.getUserName() == null || theEntry.getUserName().isEmpty()) {
            return new ResponseEntity<>("Username cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        User existingUser = userService.findByUserName(theEntry.getUserName());
        if (existingUser != null) {
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
        try {
            userService.saveNewAdminUser(theEntry);
            return new ResponseEntity<>(theEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }



}

package com.devpanwar.journalApp.controller;


import com.devpanwar.journalApp.entity.User;
import com.devpanwar.journalApp.service.JournalEntryService;
import com.devpanwar.journalApp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {


    @Autowired
    private UserService userService;

    //    for logging purposes...to prevent making this instance for all classes we simply use @slf4j lombok annotation at top of class...then we can directly use the logging properties as log.error(..,..,..)
    private static final Logger logger= LoggerFactory.getLogger(PublicController.class);


    @PostMapping("/create")
    public ResponseEntity<?> createEntry(@RequestBody User theEntry) {
        if (theEntry.getUserName() == null || theEntry.getUserName().isEmpty()) {
            return new ResponseEntity<>("Username cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        User existingUser = userService.findByUserName(theEntry.getUserName());
        if (existingUser != null) {
//            {} will be replaced by variable written after ,
            logger.info("Username already exists with username {}",existingUser.getUserName());
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }

        try {
            userService.saveNewUser(theEntry);
            return new ResponseEntity<>(theEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

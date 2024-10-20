package com.devpanwar.journalApp.controller;


import com.devpanwar.journalApp.api.response.WeatherResponse;
import com.devpanwar.journalApp.entity.JournalEntry;
import com.devpanwar.journalApp.entity.User;
import com.devpanwar.journalApp.repository.UserRepository;
import com.devpanwar.journalApp.service.UserService;
import com.devpanwar.journalApp.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
//        authentication username and password
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        getting username after authentication is successful
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(userInDb,HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser() {
//        authentication username and password
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        getting username after authentication is successful
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userRepository.deleteByUserName(userName);
        return new ResponseEntity<>(userInDb,HttpStatus.NO_CONTENT);

    }

    @GetMapping
    public ResponseEntity<?> greetings() {
//        authentication username and password
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        getting username after authentication is successful
        String userName = authentication.getName();
        WeatherResponse weather=weatherService.getWeather("Jodhpur");
        String greeting="";
        if (weather!=null){
            greeting="Weather feels like "+weather.getCurrent().getFeelslike();
        }
        log.info(userName);
        return new ResponseEntity<>("Hi "+userName+" "+greeting,HttpStatus.OK);

    }





}
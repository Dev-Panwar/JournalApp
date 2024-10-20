package com.devpanwar.journalApp.repository;

import com.devpanwar.journalApp.entity.JournalEntry;
import com.devpanwar.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);

    void deleteByUserName(String username);

}
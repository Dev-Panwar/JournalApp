package com.devpanwar.journalApp.repository;


// for fetching api related stuffs from DB

import com.devpanwar.journalApp.entity.ConfigJournalAppEntity;
import com.devpanwar.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}

package com.devpanwar.journalApp.cache;

import com.devpanwar.journalApp.entity.ConfigJournalAppEntity;
import com.devpanwar.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public enum keys{
        WEATHER_API;
    }

//    when during post-construct, this Bean is created only once and hence we can use it as in memory Cache.
    public Map<String,String> appCache;

    @PostConstruct
    public void init(){
//        when we make changes to DB and to get new endpoints and Keys
        appCache=new HashMap<>();
        //    we will store all the api data such as endpoints and keys and use it as required without calling DB again again for this info
        List<ConfigJournalAppEntity> all=configJournalAppRepository.findAll();
//         storing in appCache Map
        for (ConfigJournalAppEntity configJournalAppEntity:all){
            appCache.put(configJournalAppEntity.getKey(),configJournalAppEntity.getValue());
        }
    }


}

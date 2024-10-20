package com.devpanwar.journalApp.repository;


//for learning mongoDB template, criteria and Query for advanced queries

import com.devpanwar.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSentimentAnalysis(){
//        criteria means setting up rules on which basis we are selecting something...and we run criteria using Query
        Query query=new Query();
//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));
//        if we want to match the email if regular expression...regex
        query.addCriteria(Criteria.where("email").regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}$"));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));


//        to add multiple values...we use "in" and nin(not in)
//        query.add(Criteria.where("Roles").in("ADMIN","MANAGER",..)



//        when we have multiple criteria...instead of doing above things to add criteria again & again we can do:
//        Criteria criteria=new Criteria(Criteria.where("email").exists(true),Criteria.where("sentimentAnalysis").is(true));
//        Criteria criteria=new Criteria();
        //        for and or or
//        query.addCriteria(criteria.orOperator(
//                Criteria.where("email").exists(true),Criteria.where("sentimentAnalysis").is(true)
//        ))

//        giving class where we want to run the query
        List<User> users=mongoTemplate.find(query,User.class);
        return users;
    }
//     query.addCriteria(Criteria.where("userName").is("devpanwar"));
////        ne means not equal, lt means less than, lte means less than equal, gt means greater than, gte means greater than equal to
////        query.addCriteria(Criteria.where("userName").ne("devpanwar"));
}

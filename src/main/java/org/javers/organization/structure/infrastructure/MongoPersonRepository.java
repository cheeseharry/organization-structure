package org.javers.organization.structure.infrastructure;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.javers.organization.structure.domain.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoPersonRepository {

    private static final String COLLECTION_NAME = Person.class.getSimpleName();

    private final DB mongo;
    private final Gson gson;

    @Autowired
    public MongoPersonRepository(DB mongo, Gson gson) {
        this.mongo = mongo;
        this.gson = gson;
    }

    public void update(Person person) {
        DBCollection collection = mongo.getCollection(COLLECTION_NAME);
        DBObject retrived = collection.findOne(new BasicDBObject("id", person.getId()));
        collection.update(retrived, (DBObject) JSON.parse(gson.toJson(person)));
    }

    public void save(Person person) {
        DBObject dbObject = (DBObject) JSON.parse(gson.toJson(person));
        mongo.getCollection(COLLECTION_NAME).save(dbObject);
    }

    public Person findPerson(long id) {
        DBObject dbObject = mongo.getCollection(COLLECTION_NAME).findOne(new BasicDBObject("id", id));

        return gson.fromJson(JSON.serialize(dbObject), Person.class);
    }
}

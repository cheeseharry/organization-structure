package org.javers.organization.structure.infrastructure;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import org.javers.organization.structure.domain.Hierarchy;
import org.javers.organization.structure.domain.HierarchyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MongoHierarchyRepository implements HierarchyRepository {

    public static final String COLLECTION_NAME = Hierarchy.class.getSimpleName();
    private DB db;
    private final Gson gson;

    @Autowired
    public MongoHierarchyRepository(DB db, Gson gson) {
        this.db = db;
        this.gson = gson;
    }

    @Override
    public void save(Hierarchy hierarchy) {
        db.getCollection(COLLECTION_NAME).save((DBObject) JSON.parse(gson.toJson(hierarchy)));
    }

    @Override
    public List<Hierarchy> findAll() {
        DBCursor coursor = db.getCollection(COLLECTION_NAME).find();

        return StreamSupport.stream(coursor.spliterator(), false)
                .map((o) -> gson.fromJson(JSON.serialize(o), Hierarchy.class))
                .collect(Collectors.toList());
    }

    @Override
    public Hierarchy find(String hierarchyName) {
        DBObject dbObject = db.getCollection(COLLECTION_NAME).findOne(new BasicDBObject("hierarchyName", hierarchyName));

        return gson.fromJson(JSON.serialize(dbObject), Hierarchy.class);
    }

    @Override
    public void update(Hierarchy selected) {
        db.getCollection(COLLECTION_NAME)
                .findAndModify(new BasicDBObject("hierarchyName", selected.getHierarchyName()), (DBObject) JSON.parse(gson.toJson(selected)));
    }
}

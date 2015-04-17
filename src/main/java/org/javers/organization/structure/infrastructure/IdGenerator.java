package org.javers.organization.structure.infrastructure;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    private DB db;

    @Autowired
    public IdGenerator(DB db) {
        this.db = db;
    }

    long idFor(Class objectClass) {
        DBCursor max = db.getCollection(objectClass.getSimpleName())
                .find()
                .sort(new BasicDBObject("id", -1))
                .limit(1);

        if (max.hasNext()) {
            return ((BasicDBObject) max.next()).getLong("id");
        } else {
            return 1L;
        }
    }
}

package org.javers.organization.structure.infrastructure;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class DbFactory implements FactoryBean<DB>{

    @Override
    public DB getObject() throws Exception {
        return new MongoClient().getDB("test");
    }

    @Override
    public Class<?> getObjectType() {
        return DB.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}

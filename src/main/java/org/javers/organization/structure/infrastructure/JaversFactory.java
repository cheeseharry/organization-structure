package org.javers.organization.structure.infrastructure;

import com.mongodb.DB;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.repository.api.JaversRepository;
import org.javers.repository.mongo.MongoRepository;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JaversFactory implements FactoryBean<Javers>{

    private DB db;

    @Autowired
    public JaversFactory(DB db) {
        this.db = db;
    }

    @Override
    public Javers getObject() throws Exception {
        JaversRepository javersRepository = new MongoRepository(db);

        Javers javers = JaversBuilder.javers()
                .registerJaversRepository(javersRepository)
                .build();

        return javers;
    }

    @Override
    public Class<?> getObjectType() {
        return Javers.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

package org.javers.organization.structure.infrastructure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class JsonMapperFactory implements FactoryBean<Gson> {

    @Override
    public Gson getObject() throws Exception {
        Gson gson = new GsonBuilder().create();
        return gson;
    }

    @Override
    public Class<?> getObjectType() {
        return Gson.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

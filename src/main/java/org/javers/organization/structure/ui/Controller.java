package org.javers.organization.structure.ui;

import com.google.common.collect.Lists;
import org.javers.organization.structure.domain.Hierarchy;
import org.javers.organization.structure.domain.Person;
import org.javers.organization.structure.infrastructure.DataInitializer;
import org.javers.organization.structure.infrastructure.MongoPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {

    private MongoPersonRepository personRepository;

    @Autowired
    public Controller(MongoPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void updatePerson(Person person) {
        personRepository.update(person);
    }

    public List<Hierarchy> getAllHierachies() {
        return Lists.newArrayList(DataInitializer.getHierachy());
    }
}

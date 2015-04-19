package org.javers.organization.structure.domain;

import org.javers.organization.structure.infrastructure.UserContext;
import org.javers.spring.annotation.JaversAuditable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonService {

    private PersonRepository personRepository;

    private UserContext userContext;

    @Autowired
    public PersonService(PersonRepository personRepository, UserContext userContext) {
        this.personRepository = personRepository;
        this.userContext = userContext;
    }

    @JaversAuditable
    public void update(Person person) {
        personRepository.update(person);
    }

    public Person findPerson(String login) {
        return personRepository.find(login);
    }
}

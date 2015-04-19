package org.javers.organization.structure.domain;

import org.javers.core.Javers;
import org.javers.organization.structure.infrastructure.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonService {

    private PersonRepository personRepository;

    private Javers javers;

    private UserContext userContext;

    @Autowired
    public PersonService(PersonRepository personRepository, Javers javers, UserContext userContext) {
        this.personRepository = personRepository;
        this.javers = javers;
        this.userContext = userContext;
    }

    public void update(Person person) {
        personRepository.update(person);

        javers.commit(userContext.getLoggedUser(), person);
    }

    public Person findPerson(String login) {
        return personRepository.find(login);
    }
}

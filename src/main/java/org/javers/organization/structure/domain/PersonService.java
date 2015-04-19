package org.javers.organization.structure.domain;

import org.javers.core.Javers;
import org.javers.organization.structure.infrastructure.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonService {

    private PersonRepository personRepository;
    private UserContext userContext;

    private Javers javers;

    @Autowired
    public PersonService(PersonRepository personRepository, UserContext userContext, Javers javers) {
        this.personRepository = personRepository;
        this.userContext = userContext;
        this.javers = javers;
    }

    public void update(Person person) {
        personRepository.update(person);

        javers.commit(userContext.getLoggedUser(), person);
    }

    public Person findPerson(String login) {
        return personRepository.find(login);
    }
}

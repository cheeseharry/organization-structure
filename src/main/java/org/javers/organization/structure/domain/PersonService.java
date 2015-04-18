package org.javers.organization.structure.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void update(Person person) {
        personRepository.update(person);
    }

    public Person findPerson(long id) {
        return personRepository.find(id);
    }
}

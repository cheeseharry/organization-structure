package org.javers.organization.structure.domain;

public interface PersonRepository {

    void update(Person person);

    void save(Person person);

    Person find(String login);
}

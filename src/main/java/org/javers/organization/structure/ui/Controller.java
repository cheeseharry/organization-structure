package org.javers.organization.structure.ui;

import org.javers.organization.structure.domain.Employee;
import org.javers.organization.structure.domain.Hierarchy;
import org.javers.organization.structure.domain.Person;
import org.javers.organization.structure.infrastructure.HierarchyRepository;
import org.javers.organization.structure.infrastructure.MongoPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Controller {

    private MongoPersonRepository personRepository;

    private HierarchyRepository hierarchyRepository;
    private MainView mainView;

    @Autowired
    public Controller(MongoPersonRepository personRepository, HierarchyRepository hierarchyRepository) {
        this.personRepository = personRepository;
        this.hierarchyRepository = hierarchyRepository;
    }

    public void updatePerson(Person person) {
        personRepository.update(person);
    }

    public List<Hierarchy> getHierarchyList() {
        return hierarchyRepository.findAll();
    }

    public void updateHierarchy(Hierarchy selected) {
        hierarchyRepository.updateHierarchy(selected);
    }

    public void employeeSelected(Employee e) {
        Person person = personRepository.findPerson(e.getId());

        mainView.selectPersonOnForm(person);
    }

    public void injectView(MainView mainView) {
        this.mainView = mainView;
    }
}

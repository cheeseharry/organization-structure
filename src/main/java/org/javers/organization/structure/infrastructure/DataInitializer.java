package org.javers.organization.structure.infrastructure;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.mongodb.DB;
import org.javers.organization.structure.domain.Employee;
import org.javers.organization.structure.domain.Hierarchy;
import org.javers.organization.structure.domain.Person;
import org.javers.organization.structure.domain.Sex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.javers.organization.structure.domain.Position.CTO;
import static org.javers.organization.structure.domain.Position.DEVELOPER;
import static org.javers.organization.structure.domain.Position.IT_MANAGER;
import static org.javers.organization.structure.domain.Position.SCRUM_MASTER;
import static org.javers.organization.structure.domain.Position.TEAM_LEAD;

/**
 * @author bartosz walacik
 */
@Component
public class DataInitializer {

    Person frodo;
    Person bilbo;
    Person sam;
    Person merry;
    Person lucy;
    Person eva;
    Person charlie;
    Person kaz;
    Person stef;
    Person bob;
    List<Person> persons;

    Employee eFrodo;
    Employee eBilbo;
    Employee eSam;
    Employee eMerry;
    Employee eLucy;
    Employee eEva;
    Employee eCharlie;
    Employee eKaz;
    Employee eStef;
    Employee eBob;

    public DataInitializer() {
        frodo = new Person(1l, "frodo", "Frodo", "Baggins", Sex.MALE).assignPosition(DEVELOPER, 9_000);
        bilbo = new Person(2l, "bilbo", "Bilbo", "Baggins", Sex.MALE).assignPosition(SCRUM_MASTER, 10_000);
        sam = new Person(3l, "sam", "Sam", "Gamgee", Sex.MALE).assignPosition(DEVELOPER, 11_000);
        merry = new Person(4l, "merry", "Meriadoc", "Brandybuck", Sex.MALE).assignPosition(DEVELOPER, 12_000);
        lucy = new Person(5l, "lucy", "Lucy","Valinor", Sex.FEMALE).assignPosition(TEAM_LEAD, 13_000);
        eva = new Person(6l, "eva", "Eva","Celebrimbor", Sex.FEMALE).assignPosition(TEAM_LEAD, 14_000);
        charlie = new Person(7l, "charlie","Charlie","Big", Sex.MALE).assignPosition(TEAM_LEAD, 15_000);
        kaz = new Person(8l, "kaz", "Mad","Kaz", Sex.MALE).assignPosition(IT_MANAGER, 16_000);
        stef = new Person(9l, "stef", "Crazy","Stefan", Sex.MALE).assignPosition(IT_MANAGER, 17_000);
        bob = new Person(10l, "bob", "Uncle","Bob", Sex.MALE).assignPosition(CTO, 20_000);
        persons = Lists.newArrayList(frodo, bilbo, sam, merry, lucy, eva, charlie, kaz, stef, bob);

        eFrodo = frodo.toEmployee();
        eBilbo = bilbo.toEmployee();
        eSam = sam.toEmployee();
        eMerry = merry.toEmployee();
        eLucy = lucy.toEmployee();
        eEva = eva.toEmployee();
        eCharlie = charlie.toEmployee();
        eKaz = kaz.toEmployee();
        eStef = stef.toEmployee();
        eBob = bob.toEmployee();
    }

    @Autowired
    private MongoHierarchyRepository hierarchyRepository;
    
    @Autowired
    private MongoPersonRepository mongoPersonRepository;

    @Autowired
    private DB db;

    public void populate() {
        db.getCollection(Person.class.getSimpleName()).drop();
        db.getCollection(Hierarchy.class.getSimpleName()).drop();

        persons.forEach(p -> mongoPersonRepository.save(p));

        hierarchyRepository.save(new Hierarchy(createBobTree(), "Hier_2013"));
        Employee bobNew = createBobTree();
        Employee kazNew = bobNew.getSubordinate("kaz");
        Employee stefNew = bobNew.getSubordinate("stef");
        kazNew.addSubordinate(stefNew.getSubordinate("charlie"));
        hierarchyRepository.save(new Hierarchy(bobNew,"Hier_2014"));
    }

    public  Employee createBobTree() {
        eLucy.addSubordinates(Lists.newArrayList(eFrodo, eBilbo));
        eEva.addSubordinates(Lists.newArrayList(eSam, eMerry));
        eKaz.addSubordinates(Lists.newArrayList(eLucy, eEva));
        eStef.addSubordinate(eCharlie);
        return eBob.addSubordinates(Lists.newArrayList(eKaz, eStef));
    }
}

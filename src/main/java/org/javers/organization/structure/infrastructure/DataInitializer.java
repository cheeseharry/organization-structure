package org.javers.organization.structure.infrastructure;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import org.javers.organization.structure.domain.Employee;
import org.javers.organization.structure.domain.Hierarchy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author bartosz walacik
 */
@Component
public class DataInitializer {

    @Autowired
    private HierarchyRepository hierarchyRepository;

    public void populate() {
        hierarchyRepository.save(new Hierarchy(createBobTree(), "Hier_2013"));
        Employee bobNew = createBobTree();
        Employee kazNew = bobNew.getSubordinate("kaz");
        Employee stefNew = bobNew.getSubordinate("stef");
        kazNew.addSubordinate(stefNew.getSubordinate("charlie"));
        hierarchyRepository.save(new Hierarchy(bobNew,"Hier_2014"));
    }

    public static Employee createBobTree() {
        Employee frodo = new Employee("frodo");
        Employee bilbo = new Employee("bilbo");
        Employee sam = new Employee("sam");
        Employee merry = new Employee("merry");

        Employee lucy = new Employee("lucy").addSubordinates(Lists.newArrayList(frodo, bilbo));
        Employee eva = new Employee("eva").addSubordinates(Lists.newArrayList(sam, merry));
        Employee charlie = new Employee("charlie");

        Employee kaz = new Employee("kaz").addSubordinates(Lists.newArrayList(lucy, eva));
        Employee stef = new Employee("stef").addSubordinate(charlie);

        return new Employee("bob").addSubordinates(Lists.newArrayList(kaz, stef));
    }
}

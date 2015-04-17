package org.javers.organization.structure.infrastructure;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import org.javers.organization.structure.domain.Employee;
import org.javers.organization.structure.domain.Hierarchy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author bartosz walacik
 */
@Service
public class DataInitializer {

    @Autowired
    private HierarchyRepository hierarchyRepository;

    public static Hierarchy getHierachy() {
        return new Hierarchy(createBobTree(),"Hier_2013");
    }

    void populate(){
        hierarchyRepository.save(new Hierarchy(createBobTree(),"Hier_2013"));
//        Employee bobNew = creaq1  §   §§1q§§qqq§11qq1qateBobTree();
//        Employee lucyNew = bobNew.getSubordinate('lucy');
//        Employee evaNew = bobNew.getSubordinate('eva');
//        lucyNew.addSubordinate(evaNew.getSubordinate('merry'));              //merry has new boss
//        lucyNew.assignPosition(org.javers.democlient.domain.Position.TEAM_LEAD,13500);                             //lucy got a rise
//        lucyNew.getSubordinate('frodo').assignPosition(org.javers.democlient.domain.Position.SCRUM_MASTER,9_000);  //frodo got a new position
//        hierarchyService.save(new Hierarchy(bobNew,"Hier_2014"));
    }

    public static Employee createBobTree(){

        Employee frodo = new Employee("frodo");
        Employee bilbo = new Employee("bilbo");
        Employee sam = new Employee("sam");
        Employee merry = new Employee("merry");

        Employee lucy     = new Employee("lucy").addSubordinates(Lists.newArrayList(frodo,bilbo));
        Employee eva      = new Employee("eva").addSubordinates(Lists.newArrayList(sam,merry));
        Employee charlie  = new Employee("charlie");

        Employee kaz  = new Employee("kaz").addSubordinates(Lists.newArrayList(lucy, eva));
        Employee stef = new Employee("stef").addSubordinate(charlie);

        return new Employee("bob").addSubordinates(Lists.newArrayList(kaz,stef));
    }
}

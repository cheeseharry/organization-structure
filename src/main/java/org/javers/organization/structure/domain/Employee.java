package org.javers.organization.structure.domain;

import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bartosz walacik
 */
//@Document
public class Employee implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Employee.class);

//    @Id
    private String login;

//    @Transient
    private Employee boss;

    private List<Employee> subordinates = new ArrayList<>();

    public Employee(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public Employee addSubordinates(List<Employee> subordinates){
//        Preconditions.checkArgument(subordinates != null);
        subordinates.forEach(s-> addSubordinate(s));
        return this;
    }

    public Employee addSubordinate(Employee subordinate){
//        Preconditions.checkArgument(subordinate != null);

        if (subordinate.boss!=null){
            subordinate.boss.subordinates.remove(subordinate);
        }
        subordinate.boss = this;

        subordinates.add(subordinate);

        //logger.info("subordinate [{}] has a new boss [{}]", subordinate.getLogin(), this.getLogin());

        return this;
    }

    public List<Employee> getAllEmployees() {
        return ImmutableList.copyOf(subordinates);
    }
}

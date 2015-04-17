package org.javers.organization.structure.domain;

import com.google.common.collect.ImmutableList;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author bartosz walacik
 */
//@Document
public class Employee implements Serializable {
    @Id
    private String login;

    private transient Employee boss;

    private List<Employee> subordinates = new ArrayList<>();

    public Employee(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public Employee addSubordinates(List<Employee> subordinates){
        subordinates.forEach(s-> addSubordinate(s));
        return this;
    }

    public Employee addSubordinate(Employee subordinate){
        if (subordinate.boss!=null){
            subordinate.boss.subordinates.remove(subordinate);
        }
        subordinate.boss = this;

        subordinates.add(subordinate);

        return this;
    }

    public List<Employee> getAllEmployees() {
        return ImmutableList.copyOf(subordinates);
    }

    public Employee getSubordinate(String name) {
        return subordinates.stream().filter(s -> s.getLogin().equals(name)).findFirst().get();
    }
}

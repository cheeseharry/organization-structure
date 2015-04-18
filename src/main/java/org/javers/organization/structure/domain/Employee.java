package org.javers.organization.structure.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bartosz walacik
 */
//@Document
public class Employee {

    private long id;
    private String login;
    private transient Employee boss;

    private List<Employee> subordinates = new ArrayList<>();

    public Employee(long id, String login) {
        this.id = id;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public Employee addSubordinates(List<Employee> subordinates) {
        subordinates.forEach(s -> addSubordinate(s));
        return this;
    }

    public Employee addSubordinate(Employee subordinate) {
        if (subordinate.boss != null) {
            subordinate.boss.subordinates.remove(subordinate);
        }
        subordinate.boss = this;

        subordinates.add(subordinate);

        return this;
    }

    public void removeSubordinate(Employee employee) {
        subordinates.remove(employee);
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public List<Employee> getAllEmployees() {
        return subordinates;
    }

    public Employee getSubordinate(String name) {
        return subordinates.stream().filter(s -> s.getLogin().equals(name)).findFirst().get();
    }

    public long getId() {
        return id;
    }
}


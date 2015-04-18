package org.javers.organization.structure.domain;

/**
 * @author bartosz walacik
 */
public class Person {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private Sex sex;
    private Integer salary;
    private Position position;

    public Person() {
    }

    public Person(long id, String login, String firstName, String lastName, Sex sex) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.sex = sex;
    }

    public Person assignPosition(Position position, int salary) {
        this.position = position;
        this.salary = salary;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Employee toEmployee() {
        return new Employee(id, login);
    }
}


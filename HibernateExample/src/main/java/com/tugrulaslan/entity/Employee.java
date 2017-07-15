package com.tugrulaslan.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 90003327 on 7/10/2014.
 */
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false, unique=true, length=11)
    private int id;

    @Column(name="name", length=20, nullable=true)
    private String name;

    @Column(name="lastname", length=20, nullable=true)
    private String lastname;

    @Column(name="reg_date", nullable=true)
    private Date regDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", regDate=" + regDate +
                '}';
    }
}

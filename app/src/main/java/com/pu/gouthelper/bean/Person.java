package com.pu.gouthelper.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Requiem on 2016/3/18.
 * DbManager db = x.getDb(((AppContext) getApplicationContext()).getDaoConfig());
 * Person p = new Person();
 * p.setName(username);
 * p.setAge(password);
 * try {
 * db.save(p);
 * } catch (DbException e) {
 * e.printStackTrace();
 * }
 * //  List<Person> lyjPersons=db.selector(Person.class).findAll();
 */
@Table(name = "person")
public class Person {
    @Column(name = "id", isId = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private String age;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

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
}
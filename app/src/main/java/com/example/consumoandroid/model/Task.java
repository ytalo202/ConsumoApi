package com.example.consumoandroid.model;

public class Task {

    private Integer id;
    private String name;
    private Integer value;
    private Integer type;

    public Task(String name, Integer value, Integer type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Task(Integer id, String name, Integer value, Integer type) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

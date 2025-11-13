package com.exercise.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;


@Entity
public class Country {

    @Id
    private String name;

    private String description;


    //constructor

    public Country() {}
    public Country(String name, String description){
        this.name = name;
        this.description = description;
    }

    //getters and setters

    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

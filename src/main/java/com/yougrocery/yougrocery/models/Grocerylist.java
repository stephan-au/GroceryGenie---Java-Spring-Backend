package com.yougrocery.yougrocery.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "grocery_list")
public class Grocerylist {
    @Id
    @GeneratedValue(generator = "seq_grocerylist")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    public Grocerylist(String name) {
        this.name = name;
    }
}
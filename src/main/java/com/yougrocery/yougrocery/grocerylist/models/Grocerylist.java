package com.yougrocery.yougrocery.grocerylist.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "grocery_list")
public class Grocerylist {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grocerylist_generator")
    @SequenceGenerator(name = "grocerylist_generator", sequenceName = "seq_grocerylist", allocationSize = 1)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    public Grocerylist(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;

        Grocerylist that = (Grocerylist) o;
        return Objects.equals(this.id, that.id)
                && Objects.equals(this.name, that.name);
    }
}
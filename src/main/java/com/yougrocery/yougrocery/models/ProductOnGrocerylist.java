package com.yougrocery.yougrocery.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_on_grocery_list", uniqueConstraints = {
        @UniqueConstraint(name = "uc_productongrocerylist", columnNames = {"product_id", "grocery_list_id"})
})
public class ProductOnGrocerylist {
    @Id
    @GeneratedValue(generator = "seq_productongrocerylist")
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "grocery_list_id", nullable = false)
    private Grocerylist groceryList;
}
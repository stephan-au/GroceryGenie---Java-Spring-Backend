package com.yougrocery.yougrocery.models;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "grocery_item", uniqueConstraints = {
        @UniqueConstraint(name = "uc_groceryitem", columnNames = {"product_id", "grocery_list_id"})
})
public class GroceryItem {
    @Id
    @GeneratedValue(generator = "seq_groceryitem")
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "grocery_list_id", nullable = false)
    private Grocerylist groceryList;

    private int amount;

    public GroceryItem(Product product, Grocerylist groceryList, int amount) {
        this.product = product;
        this.groceryList = groceryList;
        this.amount = amount;
    }
}
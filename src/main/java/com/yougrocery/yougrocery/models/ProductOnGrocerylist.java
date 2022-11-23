package com.yougrocery.yougrocery.models;

import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
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
    private int id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "grocery_list_id", nullable = false)
    private Grocerylist groceryList;

    private int amount;

    public ProductOnGrocerylist(Product product, Grocerylist groceryList, int amount) {
        this.product = product;
        this.groceryList = groceryList;
        this.amount = amount;
    }
}
package com.yougrocery.yougrocery.repositories;

import com.yougrocery.yougrocery.models.Grocerylist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GrocerylistRepositoryTest {

    @Autowired
    private GrocerylistRepository repo;

    @Test
    void createTwoGroceryListsWithSameNameWorks() {
        Grocerylist g1 = new Grocerylist("Test name 1");
        Grocerylist g2 = new Grocerylist("Test name 1");

        repo.save(g1);
        repo.save(g2);

        assertThat(repo.findAll())
                .hasSize(2)
                .extracting(Grocerylist::getName)
                .containsExactly(
                        "Test name 1",
                        "Test name 1");
    }
}
package com.hobos.freeter.post;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryServiceTest {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    private CategoryService categoryService;


    @BeforeEach
    public void beforeAll() {
        Category categoryA = Category.builder().name("a").build();
        Category categoryB = Category.builder().name("b").build();
        Category categoryAA = Category.builder().name("aa").build();
        categoryA.addChildren(categoryAA);
        entityManager.persist(categoryA);
        entityManager.persist(categoryB);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void getCategories() {
        List<Category> categories = categoryService.getCategories();

        assertEquals(2, categories.size());
        assertEquals("a", categories.get(0).getName());
        assertEquals("aa", categories.get(0).getChildren().get(0).getName());
        assertEquals("b", categories.get(1).getName());
        assertEquals(0, categories.get(1).getChildren().size());
        assertEquals(1, categories.get(0).getChildren().size());
    }
}
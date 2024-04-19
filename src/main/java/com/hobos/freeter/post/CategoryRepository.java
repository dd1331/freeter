package com.hobos.freeter.post;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.children where c.parent is null")
    List<Category> findAllCategoriesWithChildren();


}

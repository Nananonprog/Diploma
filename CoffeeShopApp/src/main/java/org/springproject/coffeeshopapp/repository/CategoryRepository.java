package org.springproject.coffeeshopapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springproject.coffeeshopapp.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    public Boolean existsByName(String name);
}


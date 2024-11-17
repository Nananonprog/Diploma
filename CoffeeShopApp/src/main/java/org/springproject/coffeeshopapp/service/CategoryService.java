package org.springproject.coffeeshopapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springproject.coffeeshopapp.entity.Category;
import org.springproject.coffeeshopapp.repository.CategoryRepository;

import java.util.List;
@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Boolean existCategory(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public Boolean deleteCategory(int id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if(category != null) {
            categoryRepository.delete(category);
            return true;
        }
        return false;
    }
}

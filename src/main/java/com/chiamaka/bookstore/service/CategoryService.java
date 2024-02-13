package com.chiamaka.bookstore.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import com.chiamaka.bookstore.repository.CategoryRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.chiamaka.bookstore.model.Category;

@Service
@Transactional
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> listCategories() {
		return categoryRepository.findAll();
	}

	public void createCategory(Category category) {
		categoryRepository.save(category);
	}

	public Category readCategory(String title) {
		return categoryRepository.findByTitle(title);
	}

	public Optional<Category> readCategory(Integer categoryId) {
		return categoryRepository.findById(categoryId);
	}

	public void updateCategory(Integer categoryID, Category newCategory) {
		Category category = categoryRepository.findById(categoryID).get();
		category.setTitle(newCategory.getTitle());
		category.setDescription(newCategory.getDescription());
//		category.setBooks(newCategory.getBooks());
		category.setImageUrl(newCategory.getImageUrl());

		categoryRepository.save(category);
	}

	public List<Category> findAllCategory(Pageable pageable) {

		List<Category> categories = categoryRepository.findAll(pageable).getContent();
		if(categories.size() > 0){
			return categories;
		}else {
			return new ArrayList<Category>();
		}
	}

	public long getCategoryCount() {
		return categoryRepository.count();
	}
}

package com.chiamaka.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import com.chiamaka.bookstore.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.chiamaka.bookstore.common.ApiResponse;
import com.chiamaka.bookstore.model.Category;
import com.chiamaka.bookstore.service.CategoryService;

@RestController
@RequestMapping("/category")

public class CategoryController {

	@Autowired
	private CategoryService categoryService;

//	@GetMapping("/")
//    public ResponseEntity<List<Category>> getCategories() {
//        List<Category> body = categoryService.listCategories();
//        return new ResponseEntity<List<Category>>(body, HttpStatus.OK);
//    }

	@GetMapping
	public ResponseEntity<?> getAllCategories(@RequestParam(name = "_start", defaultValue = "0") int page,
											  @RequestParam(name = "_end", required = false, defaultValue = "30") int pageSize,
											  @RequestParam(name = "sort", required = false, defaultValue = "id") String sortTerm){
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortTerm).descending());
		List<Category> category = categoryService.findAllCategory(pageable);
		if(category.size() > 0){
			HttpHeaders headers = new HttpHeaders(){
				{
					add("Access-Control-Expose-Headers", "Content-Range");
					add("Content-Range", String.valueOf(categoryService.getCategoryCount()));
				}
			};

//        return new ResponseEntity<>(branch, HttpStatus.OK);
			return ResponseEntity.ok().headers(headers).body(category);
		}else {
			return new ResponseEntity<>("No category available", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category) {
		if (Helper.notNull(categoryService.readCategory(category.getTitle()))) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category already exists"), HttpStatus.CONFLICT);
		}
		categoryService.createCategory(category);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "created the category"), HttpStatus.CREATED);
	}

	@PostMapping("/update/{categoryID}")
	public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryID") Integer categoryID, @Valid @RequestBody Category category) {
		// Check to see if the category exists.
		if (Helper.notNull(categoryService.readCategory(categoryID))) {
			// If the category exists then update it.
			categoryService.updateCategory(categoryID, category);
			return new ResponseEntity<ApiResponse>(new ApiResponse(true, "updated the category"), HttpStatus.OK);
		}

		// If the category doesn't exist then return a response of unsuccessful.
		return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exist"), HttpStatus.NOT_FOUND);
	}
}

package com.chiamaka.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Data
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "title")
	private @NotBlank String title;

	private @NotBlank String description;

	private @NotBlank String imageUrl;

	// add imageURL here

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
			cascade = CascadeType.ALL)
	Set<Book> books;

	public Category() {
	}

//	public Category(@NotBlank String categoryName, @NotBlank String description) {
//		this.categoryName = categoryName;
//		this.description = description;
//	}
//
//	public Category(@NotBlank String categoryName, @NotBlank String description, @NotBlank String imageUrl) {
//		this.categoryName = categoryName;
//		this.description = description;
//		this.imageUrl = imageUrl;
//	}

//	public String getCategoryName() {
//		return this.categoryName;
//	}
//
//	public void setCategoryName(String categoryName) {
//		this.categoryName = categoryName;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Book> getProducts() {
		return books;
	}

	public void setProducts(Set<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "User {category id=" + id + ", title='" + title + "', description='" + description + "'}";
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}

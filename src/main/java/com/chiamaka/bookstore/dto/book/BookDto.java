package com.chiamaka.bookstore.dto.book;

import com.chiamaka.bookstore.model.Book;
import com.chiamaka.bookstore.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@RequiredArgsConstructor
public class BookDto {

    private Integer id;
    private MultipartFile file;
    private @NotNull String name;
//    private String imageURL;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull Integer categoryId;

//    public BookDto(Book book) {
//        this.setId(book.getId());
//        this.setName(book.getName());
////        this.setImageURL(book.getImageURL());
//        this.setDescription(book.getDescription());
//        this.setPrice(book.getPrice());
//        this.setCategoryId(book.getCategory().getId());
//    }
//
//    public BookDto(@NotNull String name, @NotNull double price, @NotNull String description, @NotNull Integer categoryId) {
//        this.name = name;
////        this.imageURL = imageURL;
//        this.price = price;
//        this.description = description;
//        this.categoryId = categoryId;
//    }

    public BookDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getImageURL() {
//        return imageURL;
//    }
//
//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

//    public void setCategoryId(Integer categoryId) {
//        this.categoryId = categoryId;
//    }


}

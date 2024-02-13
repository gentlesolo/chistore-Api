package com.chiamaka.bookstore.repository;

import com.chiamaka.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}

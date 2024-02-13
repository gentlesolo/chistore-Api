package com.chiamaka.bookstore.controller;

import com.chiamaka.bookstore.common.ApiResponse;
import com.chiamaka.bookstore.dto.book.BookDto;
import com.chiamaka.bookstore.model.Book;
import com.chiamaka.bookstore.model.Category;
import com.chiamaka.bookstore.model.ResponseClass;
import com.chiamaka.bookstore.service.CategoryService;
import com.chiamaka.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    CategoryService categoryService;

//    @GetMapping("/")
//    public ResponseEntity<List<BookDto>> getBooks() {
//        List<BookDto> body = bookService.listBooks();
//        return new ResponseEntity<List<BookDto>>(body, HttpStatus.OK);
//    }



    @GetMapping
    public ResponseEntity<?> getAllBooks(@RequestParam(name = "_start", defaultValue = "0") int page,
                                              @RequestParam(name = "_end", required = false, defaultValue = "30") int pageSize,
                                              @RequestParam(name = "sort", required = false, defaultValue = "id") String sortTerm){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortTerm).descending());
        List<Book> book = bookService.findAllBook(pageable);
        if(book.size() > 0){
            HttpHeaders headers = new HttpHeaders(){
                {
                    add("Access-Control-Expose-Headers", "Content-Range");
                    add("Content-Range", String.valueOf(bookService.getBookCount()));
                }
            };

//        return new ResponseEntity<>(branch, HttpStatus.OK);
            return ResponseEntity.ok().headers(headers).body(book);
        }else {
            return new ResponseEntity<>("No book available", HttpStatus.NOT_FOUND);
        }
    }


    //use @ModelAttribute for postman
    @PostMapping("/create")
//    @RequestMapping(value = "book", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse> addBook(@RequestBody BookDto bookDto) throws IOException {
//        Optional<Category> optionalCategory = categoryService.readCategory(bookDto.getCategoryId());
//        if (!optionalCategory.isPresent()) {
//            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
//        }
//        Category category = optionalCategory.get();
        bookService.createBook(bookDto);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Book has been added"), HttpStatus.CREATED);
    }

//    @PutMapping("/update/{bookID}")
//    public ResponseEntity<ApiResponse> updateBook(@PathVariable("bookID") Integer bookID, @RequestBody @Valid BookDto bookDto) {
//        Optional<Category> optionalCategory = categoryService.readCategory(bookDto.getCategoryId());
//        if (!optionalCategory.isPresent()) {
//            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category is invalid"), HttpStatus.CONFLICT);
//        }
//        Category category = optionalCategory.get();
//        bookService.updateBook(bookID, bookDto, category);
//        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Book has been updated"), HttpStatus.OK);
//    }
}

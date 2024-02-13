package com.chiamaka.bookstore.service;

import com.chiamaka.bookstore.dto.book.BookDto;
import com.chiamaka.bookstore.exceptions.BookNotExistException;
import com.chiamaka.bookstore.model.Image;
import com.chiamaka.bookstore.model.ImageModel;
import com.chiamaka.bookstore.repository.BookRepository;
import com.chiamaka.bookstore.model.Book;
import com.chiamaka.bookstore.model.Category;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private BookRepository bookRepository;

//    public List<BookDto> listBooks() {
//        List<Book> books = bookRepository.findAll();
//        List<BookDto> bookDtos = new ArrayList<>();
//        for(Book book : books) {
//            BookDto bookDto = getDtoFromBook(book);
//            bookDtos.add(bookDto);
//        }
//        return bookDtos;
//    }


    public List<Book> findAllBook(Pageable pageable) {

        List<Book> books = bookRepository.findAll(pageable).getContent();
        if(books.size() > 0){
            return books;
        }else {
            return new ArrayList<Book>();
        }
//        return headlineRepository.findAll(pageable).getContent();
    }

//    public static BookDto getDtoFromBook(Book book) {
//        BookDto bookDto = new BookDto(book);
//        return bookDto;
//    }
//
//    public static Book getBookFromDto(BookDto bookDto, Category category) {
//        Book book = new Book(bookDto, category);
//        return book;
//    }

//    public ResponseEntity<Map> addBook(BookDto bookDto, Category category) {
////        String fileName = imageModel.getFile().getOriginalFilename();
//        try {
//            if (bookDto.getName().isEmpty()) {
//                return ResponseEntity.badRequest().build();
//            }
//            if (bookDto.getFile().isEmpty()) {
//                return ResponseEntity.badRequest().build();
//            }
//            Book book = new Book();
////            image.setName(fileName);
//            book.setName(bookDto.getName());
//            book.setDescription(bookDto.getDescription());
//            book.setPrice(bookDto.getPrice());
////            book.setCategory(bookDto.getCategoryId());
//            book.setImageURL(cloudinaryService.uploadFile(bookDto.getFile(), "folder_1"));
//            if(book.getImageURL() == null) {
//                return ResponseEntity.badRequest().build();
//            }
//            book = getBookFromDto(bookDto, category);
//            bookRepository.save(book);
////            String url = image.getUrl();
////            return ResponseEntity.ok(url);
//            return ResponseEntity.ok().body(Map.of("url", book.getImageURL()));
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

    public Book createBook(BookDto bookDto) throws IOException {
        if(bookDto == null){
            throw  new IllegalArgumentException("Argument cannot be null");
        }
//        Optional<Book> query = bookRepository.findByName(productDto.getName());
//        if(query.isPresent()){
//            throw new BusinessLogicException("Product with name " + productDto.getName() + "already exist");
//        }

        // cloudinaryService.upload(productDto.getImage().)

        Book book = new Book();
//        try{
//            if(bookDto.getFile() != null) {
//                Map<?, ?> uploadResult = cloudinaryService.upload(bookDto.getFile().getBytes(), ObjectUtils.asMap("public", "inventoryss/" + bookDto.getFile().getOriginalFilename(), "overwrite", true));
//                book.setImageURL(uploadResult.get("urls").toString());
//            }

//        book.setImageURL(cloudinaryService.uploadFile(bookDto.getFile(), "folder_1"));

//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
        book.setName(bookDto.getName());
        book.setPrice(bookDto.getPrice());
//        product.setQuantity(productDto.getQuantity());
        book.setDescription(bookDto.getDescription());
        book.setCategory(bookDto.getCategoryId());
//        book.setCategory(getCategoryIdFromCat(bookDto.getCategoryId()));
//        book = getBookFromDto(bookDto, category);

        return bookRepository.save(book);
    }

    public Integer getCategoryIdFromCat(Category category){
        return category.getId();
    }

//    public void addBook(BookDto bookDto, Category category) {
//        Book book = new Book();
////        image.setName(fileName);
//            book.setName(bookDto.getName());
//        book.setImageURL(cloudinaryService.uploadFile(bookDto.getFile(), "folder_1"));
////        if(image.getUrl() == null) {
////            return ResponseEntity.badRequest().build();
////        }
//        book = getBookFromDto(bookDto, category);
//        bookRepository.save(book);
//    }

//    public void updateBook(Integer bookID, BookDto bookDto, Category category) {
//        Book book = getBookFromDto(bookDto, category);
//        book.setId(bookID);
//        bookRepository.save(book);
//    }


    public Book getBookById(Integer bookId) throws BookNotExistException {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (!optionalBook.isPresent())
            throw new BookNotExistException("Book id is invalid " + bookId);
        return optionalBook.get();
    }

    public long getBookCount() {
        return bookRepository.count();
    }


}

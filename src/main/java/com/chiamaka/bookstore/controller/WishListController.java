package com.chiamaka.bookstore.controller;


import com.chiamaka.bookstore.dto.book.BookDto;
import com.chiamaka.bookstore.common.ApiResponse;
import com.chiamaka.bookstore.model.Book;
import com.chiamaka.bookstore.model.User;
import com.chiamaka.bookstore.model.WishList;
import com.chiamaka.bookstore.service.AuthenticationService;
import com.chiamaka.bookstore.service.BookService;
import com.chiamaka.bookstore.service.WishListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

        @Autowired
        private WishListService wishListService;

        @Autowired
        private AuthenticationService authenticationService;

//        @GetMapping("/{token}")
//        public ResponseEntity<List<BookDto>> getWishList(@PathVariable("token") String token) {
//                int user_id = authenticationService.getUser(token).getId();
//                List<WishList> body = wishListService.readWishList(user_id);
//                List<BookDto> books = new ArrayList<BookDto>();
//                for (WishList wishList : body) {
//                        books.add(BookService.getDtoFromBook(wishList.getBook()));
//                }
//
//                return new ResponseEntity<List<BookDto>>(books, HttpStatus.OK);
//        }

        @PostMapping("/add")
        public ResponseEntity<ApiResponse> addWishList(@RequestBody Book book, @RequestParam("token") String token) {
                authenticationService.authenticate(token);
                User user = authenticationService.getUser(token);
                WishList wishList = new WishList(user, book);
                wishListService.createWishlist(wishList);
                return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Add to wishlist"), HttpStatus.CREATED);

        }


}

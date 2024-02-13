package com.chiamaka.bookstore.controller;

import com.chiamaka.bookstore.dto.cart.CartDto;
import com.chiamaka.bookstore.exceptions.AuthenticationFailException;
import com.chiamaka.bookstore.exceptions.BookNotExistException;
import com.chiamaka.bookstore.exceptions.CartItemNotExistException;
import com.chiamaka.bookstore.common.ApiResponse;
import com.chiamaka.bookstore.dto.cart.AddToCartDto;
import com.chiamaka.bookstore.model.Book;
import com.chiamaka.bookstore.model.User;
//import com.webtutsplus.ecommerce.model.*;
import com.chiamaka.bookstore.service.AuthenticationService;
import com.chiamaka.bookstore.service.CartService;
import com.chiamaka.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String tokenn) throws AuthenticationFailException, BookNotExistException {
        authenticationService.authenticate(tokenn);
        User user = authenticationService.getUser(tokenn);
        Book book = bookService.getBookById(addToCartDto.getBookId());
        System.out.println("book to add"+  book.getName());
        cartService.addToCart(addToCartDto, book, user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

    }
    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        CartDto cartDto = cartService.listCartItems(user);
        return new ResponseEntity<CartDto>(cartDto,HttpStatus.OK);
    }
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<ApiResponse> updateCartItem(@RequestBody @Valid AddToCartDto cartDto,
                                                      @RequestParam("token") String token) throws AuthenticationFailException, BookNotExistException {
        authenticationService.authenticate(token);
        User user = authenticationService.getUser(token);
        Book book = bookService.getBookById(cartDto.getBookId());
        cartService.updateCartItem(cartDto, user, book);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Book has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") int itemID,@RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException {
        authenticationService.authenticate(token);
        int userId = authenticationService.getUser(token).getId();
        cartService.deleteCartItem(itemID, userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Item has been removed"), HttpStatus.OK);
    }

}

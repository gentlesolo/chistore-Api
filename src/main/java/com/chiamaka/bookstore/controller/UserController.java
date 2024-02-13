package com.chiamaka.bookstore.controller;


import com.chiamaka.bookstore.dto.ResponseDto;
import com.chiamaka.bookstore.exceptions.CustomException;
import com.chiamaka.bookstore.model.Book;
import com.chiamaka.bookstore.repository.UserRepository;
import com.chiamaka.bookstore.dto.user.SignInDto;
import com.chiamaka.bookstore.dto.user.SignInResponseDto;
import com.chiamaka.bookstore.dto.user.SignupDto;
import com.chiamaka.bookstore.exceptions.AuthenticationFailException;
import com.chiamaka.bookstore.model.User;
import com.chiamaka.bookstore.service.AuthenticationService;
import com.chiamaka.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
//@CrossOrigin(origins = "*", allowedHeaders = "*")

public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserService userService;

//    @GetMapping("/all")
//    public List<User> findAllUser(@RequestParam("token") String token) throws AuthenticationFailException {
//        authenticationService.authenticate(token);
//        return userRepository.findAll();
//    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "_start", defaultValue = "0") int page,
                                         @RequestParam(name = "_end", required = false, defaultValue = "30") int pageSize,
                                         @RequestParam(name = "sort", required = false, defaultValue = "id") String sortTerm){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortTerm).descending());
        List<User> user = userService.findAllUser(pageable);
        if(user.size() > 0){
            HttpHeaders headers = new HttpHeaders(){
                {
                    add("Access-Control-Expose-Headers", "Content-Range");
                    add("Content-Range", String.valueOf(userService.getUserCount()));
                }
            };

//        return new ResponseEntity<>(branch, HttpStatus.OK);
            return ResponseEntity.ok().headers(headers).body(user);
        }else {
            return new ResponseEntity<>("No user available", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/signup")
    public ResponseDto Signup(@RequestBody SignupDto signupDto) throws CustomException {
        return userService.signUp(signupDto);
    }

    //TODO token should be updated
    @PostMapping("/signIn")
    public SignInResponseDto Signup(@RequestBody SignInDto signInDto) throws CustomException {
        return userService.signIn(signInDto);
    }

//    @PostMapping("/updateUser")
//    public ResponseDto updateUser(@RequestParam("token") String token, @RequestBody UserUpdateDto userUpdateDto) {
//        authenticationService.authenticate(token);
//        return userService.updateUser(token, userUpdateDto);
//    }


//    @PostMapping("/createUser")
//    public ResponseDto updateUser(@RequestParam("token") String token, @RequestBody UserCreateDto userCreateDto)
//            throws CustomException, AuthenticationFailException {
//        authenticationService.authenticate(token);
//        return userService.createUser(token, userCreateDto);
//    }
}

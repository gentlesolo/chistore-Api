package com.chiamaka.bookstore.controller;

import com.chiamaka.bookstore.exceptions.AuthenticationFailException;
import com.chiamaka.bookstore.exceptions.OrderNotFoundException;
import com.chiamaka.bookstore.common.ApiResponse;
import com.chiamaka.bookstore.model.Order;
import com.chiamaka.bookstore.model.User;
import com.chiamaka.bookstore.service.AuthenticationService;
import com.chiamaka.bookstore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private AuthenticationService authenticationService;


//    // stripe create session API
//    @PostMapping("/create-checkout-session")
//    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
//        // create the stripe session
//        Session session = orderService.createSession(checkoutItemDtoList);
//        StripeResponse stripeResponse = new StripeResponse(session.getId());
//        // send the stripe session id in response
//        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
//    }

    // place order after checkout
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> placeOrder(@RequestParam(value = "token", required = false) String token, @RequestParam(value = "sessionId", required = false) String sessionId)
            throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        // retrieve user
        User user = authenticationService.getUser(token);
        // place the order
        orderService.placeOrder(user, sessionId);
        return new ResponseEntity<>(new ApiResponse(true, "Order has been placed"), HttpStatus.CREATED);
    }

    // get all orders
//    @GetMapping("/")
//    public ResponseEntity<List<Order>> getAllOrders(@RequestParam(value = "token", required = false) String token) throws AuthenticationFailException {
//        // validate token
//        authenticationService.authenticate(token);
//        // retrieve user
//        User user = authenticationService.getUser(token);
//        // get orders
//        List<Order> orderDtoList = orderService.listOrders(user);
//
//        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
//    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestParam(name = "_start", defaultValue = "0") int page,
                                         @RequestParam(name = "_end", required = false, defaultValue = "30") int pageSize,
                                         @RequestParam(name = "sort", required = false, defaultValue = "id") String sortTerm){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortTerm).descending());
        List<Order> order = orderService.findAllOrder(pageable);
        if(order.size() > 0){
            HttpHeaders headers = new HttpHeaders(){
                {
                    add("Access-Control-Expose-Headers", "Content-Range");
                    add("Content-Range", String.valueOf(orderService.getOrderCount()));
                }
            };

//        return new ResponseEntity<>(branch, HttpStatus.OK);
            return ResponseEntity.ok().headers(headers).body(order);
        }else {
            return new ResponseEntity<>("No order available", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dailyRevenue")
    public ResponseEntity<?> getDailyRevenue(@RequestParam(name = "_start", defaultValue = "0") int page,
                                          @RequestParam(name = "_end", required = false, defaultValue = "30") int pageSize,
                                          @RequestParam(name = "sort", required = false, defaultValue = "id") String sortTerm){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortTerm).descending());
        List<Order> order = orderService.findAllOrder(pageable);
        if(order.size() > 0){
            HttpHeaders headers = new HttpHeaders(){
                {
                    add("Access-Control-Expose-Headers", "Content-Range");
                    add("Content-Range", String.valueOf(orderService.getOrderCount()));
                }
            };

//        return new ResponseEntity<>(branch, HttpStatus.OK);
            return ResponseEntity.ok().headers(headers).body(order);
        }else {
            return new ResponseEntity<>("No order available", HttpStatus.NOT_FOUND);
        }
    }


    // get orderitems for an order
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable("id") Integer id, @RequestParam("token") String token)
            throws AuthenticationFailException {
        // validate token
        authenticationService.authenticate(token);
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order,HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }

    }

}

package com.chiamaka.bookstore.repository;

import com.chiamaka.bookstore.model.Order;
import com.chiamaka.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserOrderByCreatedAtDesc(User user);

}

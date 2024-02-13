package com.chiamaka.bookstore.service;

import java.util.List;
import jakarta.transaction.Transactional;

import com.chiamaka.bookstore.repository.WishListRepository;
import com.chiamaka.bookstore.model.WishList;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class WishListService {

    private final WishListRepository wishListRepository;

    public WishListService(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    public void createWishlist(WishList wishList) {
        wishListRepository.save(wishList);
    }

    public List<WishList> readWishList(Integer userId) {
        return wishListRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }
}

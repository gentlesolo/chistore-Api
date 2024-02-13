package com.chiamaka.bookstore.dto.cart;

import javax.validation.constraints.NotNull;

public class AddToCartDto {
    private Integer id;
    private @NotNull Integer bookId;
    private @NotNull Integer quantity;

    public AddToCartDto() {
    }



    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", bookId=" + bookId +
                ", quantity=" + quantity +
                ",";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

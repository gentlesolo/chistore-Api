package com.chiamaka.bookstore.exceptions;

public class BookNotExistException extends IllegalArgumentException {
    public BookNotExistException(String msg) {
        super(msg);
    }
}

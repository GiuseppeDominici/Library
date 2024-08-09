package com.library.library.controller;

import com.library.library.entity.Book;
import com.library.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return ResponseEntity.ok(book);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        try{
            bookService.updateBook(book);
            return ResponseEntity.ok(book);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/read")
    public Book readBook(@RequestParam("isbn") String isbn) {
        try{
            return bookService.getBook(isbn);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Book> deleteBook(@RequestParam("isbn") String isbn) {
        try{
            bookService.deleteBook(isbn);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

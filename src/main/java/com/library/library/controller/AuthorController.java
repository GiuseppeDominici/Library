package com.library.library.controller;

import com.library.library.entity.Author;
import com.library.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping("/save")
    public ResponseEntity<Author> saveAuthor(@RequestBody Author author) {
        try {
            authorService.addAuthor(author);
            return ResponseEntity.ok(author);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        try{
            authorService.updateAuthor(author);
            return ResponseEntity.ok(author);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/read")
    public Author readAuthor(@RequestParam("firstName") String firstName) {
        try{
            return authorService.getAuthor(firstName);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Author> deleteAuthor(@RequestParam("firstName") String firstName) {
        try{
            authorService.deleteAuthor(firstName);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

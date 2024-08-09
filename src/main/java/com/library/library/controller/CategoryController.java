package com.library.library.controller;


import com.library.library.entity.Category;
import com.library.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        try {
            categoryService.addCategory(category);
            return ResponseEntity.ok(category);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/read")
    public Category readCategory(@RequestParam("category") String category) {
        try{
            return categoryService.getCategory(category);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Category> deleteCategory(@RequestParam("category") String category) {
        try{
            categoryService.deleteCategory(category);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

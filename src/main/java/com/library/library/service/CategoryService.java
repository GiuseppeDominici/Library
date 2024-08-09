package com.library.library.service;

import com.library.library.entity.Book;
import com.library.library.entity.Category;
import com.library.library.repository.BookRepository;
import com.library.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final BookRepository bookRepository;

    private final CategoryRepository categoryRepository;

    public CategoryService(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public void addCategory(Category category) {

        if (categoryRepository.existsByName(category.getName())) {
            throw new RuntimeException("Una categoria con questo nome esiste già: " + category.getName());
        }

        Set<Book> books = category.getBooks() != null
                ? new HashSet<>(bookRepository.findAllById(category.getBooks().stream().map(Book::getId).collect(Collectors.toList())))
                : new HashSet<>();

        category.setBooks(books);

        categoryRepository.save(category);
    }

    public Category getCategory(String category) {
        return categoryRepository.findByNameIgnoreCase(category)
                .orElseThrow(() -> new RuntimeException("Categoria non trovata: " + category));
    }

    public void deleteCategory(String category) {
        Category tmpCategory = categoryRepository.findByNameIgnoreCase(category)
                .orElseThrow(() -> new RuntimeException("Categoria non trovata: " + category));

        if(tmpCategory.getBooks() != null){
            throw new RuntimeException("In questa categoria sono presenti dei libri: " + tmpCategory.getName());
        }
        categoryRepository.delete(tmpCategory);
    }
}

package com.library.library.service;

import com.library.library.entity.Author;
import com.library.library.entity.Book;
import com.library.library.entity.Category;
import com.library.library.repository.AuthorRepository;
import com.library.library.repository.BookRepository;
import com.library.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    public void addBook(Book book) {

        if (book.getIsbn() != null && bookRepository.existsByIsbn(book.getIsbn())) {
            throw new RuntimeException("Un libro con lo stesso ISBN esiste già: " + book.getIsbn());
        }

        Set<Author> autori = book.getAuthors() != null
                ? new HashSet<>(authorRepository.findAllById(book.getAuthors().stream().map(Author::getId).collect(Collectors.toList())))
                : new HashSet<>();

        Set<Category> categorie = book.getCategories() != null
                ? new HashSet<>(categoryRepository.findAllById(book.getCategories().stream().map(Category::getId).collect(Collectors.toList())))
                : new HashSet<>();

        book.setAuthors(autori);
        book.setCategories(categorie);

        bookRepository.save(book);
    }

    public Book updateBook(Book book) {

        Book tmpBook = bookRepository.findByIsbn(book.getIsbn())
                .orElseThrow(() -> new RuntimeException("Libro non trovato: " + book.getIsbn()));

            tmpBook.setTitle(book.getTitle());
            tmpBook.setIsbn(book.getIsbn());
            tmpBook.setPrice(book.getPrice());

            Set<Author> autori = book.getAuthors() != null
                    ? new HashSet<>(authorRepository.findAllById(book.getAuthors().stream().map(Author::getId).collect(Collectors.toList())))
                    : new HashSet<>();

            Set<Category> categorie = book.getCategories() != null
                    ? new HashSet<>(categoryRepository.findAllById(book.getCategories().stream().map(Category::getId).collect(Collectors.toList())))
                    : new HashSet<>();

            tmpBook.setAuthors(autori);
            tmpBook.setCategories(categorie);
            return bookRepository.save(tmpBook);

    }

    public Book getBook(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Libro non trovato: " + isbn));
    }

    public void deleteBook(String isbn){
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Libro non trovato: " + isbn));

        bookRepository.delete(book);
    }
}

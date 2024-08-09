package com.library.library.service;

import com.library.library.entity.Author;
import com.library.library.entity.Book;
import com.library.library.repository.AuthorRepository;
import com.library.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;

    public AuthorService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void addAuthor(Author author) {

        if (author.getCodeWriter() != null && authorRepository.existsByCodeWriter(author.getCodeWriter())) {
            throw new RuntimeException("Un autore con questo codice scrittore esiste già: " + author.getCodeWriter());
        }
        Set<Book> books = author.getBooks() != null
                ? new HashSet<>(bookRepository.findAllById(author.getBooks().stream().map(Book::getId).collect(Collectors.toList())))
                : new HashSet<>();

        author.setBooks(books);

        authorRepository.save(author);
    }

    public Author updateAuthor(Author author) {

        Author tmpAuthor = authorRepository.findByCodeWriter(author.getCodeWriter())
                .orElseThrow(() -> new RuntimeException("Autore non trovato: " + author.getCodeWriter()));

            tmpAuthor.setFirstName(author.getFirstName());
            tmpAuthor.setLastName(author.getLastName());
            tmpAuthor.setCodeWriter(author.getCodeWriter());

            Set<Book> books = author.getBooks() != null
                    ? new HashSet<>(bookRepository.findAllById(author.getBooks().stream().map(Book::getId).collect(Collectors.toList())))
                    : new HashSet<>();

            tmpAuthor.setBooks(books);

            return authorRepository.save(tmpAuthor);
    }

    public Author getAuthor(String codeWriter) {
        return authorRepository.findByCodeWriter(codeWriter)
                .orElseThrow(() -> new RuntimeException("Autore non trovato: " + codeWriter));
    }

    public void deleteAuthor(String codeWriter) {
        Author author = authorRepository.findByCodeWriter(codeWriter)
                .orElseThrow(() -> new RuntimeException("Autore non trovato: " + codeWriter));

        authorRepository.delete(author);
    }
}

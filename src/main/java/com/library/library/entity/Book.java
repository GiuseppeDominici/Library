package com.library.library.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    @Column(unique = true)
    private String isbn;

    private double price;

    private int quantity;

    @ManyToMany
    private Set<Author> authors;

    @ManyToMany
    private Set<Category> categories;
}

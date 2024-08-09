package com.library.library.repository;

import com.library.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    boolean existsByCodeWriter(String codeWriter);

    Optional<Author> findByCodeWriter(String codeWriter);
}

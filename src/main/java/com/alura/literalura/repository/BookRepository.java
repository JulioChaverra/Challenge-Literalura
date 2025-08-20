package com.alura.literalura.repository;

import com.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByGutenbergId(Integer gutenbergId);

    @EntityGraph(attributePaths = {"authors", "languages"})
    @Query("SELECT DISTINCT b FROM Book b ORDER BY b.title ASC")
    List<Book> findAllWithDetails();

    @EntityGraph(attributePaths = {"authors", "languages"})
    @Query("""
            SELECT DISTINCT b
            FROM Book b
            JOIN b.languages l
            WHERE l = :lang
            ORDER BY b.title ASC
            """)
    List<Book> findByLanguageWithDetails(String lang);
}

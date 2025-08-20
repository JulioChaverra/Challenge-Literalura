package com.alura.literalura.repository;

import com.alura.literalura.model.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameAndBirthYearAndDeathYear(String name, Integer birthYear, Integer deathYear);

    @EntityGraph(attributePaths = "books")
    @Query("""
           SELECT DISTINCT a
           FROM Author a
           LEFT JOIN a.books b
           ORDER BY a.name asc
           """)
    List<Author> findAllWithBooksOrderByName();

    @EntityGraph(attributePaths = "books")
    @Query("""
            SELECT DISTINCT a
            FROM Author a
            LEFT JOIN a.books b
            WHERE ((a.birthYear IS NULL OR a.birthYear <= :year)
                AND (a.deathYear IS NULL OR a.deathYear >= :year))
            ORDER BY a.name DESC
            """)
    List<Author> findAliveInYearWithBooks(Integer year);
}
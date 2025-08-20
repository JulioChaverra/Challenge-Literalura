package com.alura.literalura.service;

import com.alura.literalura.gutendex.GutendexClient;
import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecaService {

    private final GutendexClient client;
    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;

    public BibliotecaService(GutendexClient client, BookRepository bookRepo, AuthorRepository authorRepo) {
        this.client = client;
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    @Transactional
    public Optional<Book> buscarYGuardarPorTitulo(String titulo) {
        var resp = client.searchBooks(titulo);
        if (resp == null || resp.results() == null || resp.results().isEmpty()) {
            return Optional.empty();
        }

        var dto = resp.results().get(0);

        if (bookRepo.findByGutenbergId(dto.id()).isPresent()) {
            throw new IllegalStateException("No se puede agregar el mismo libro");
        }

        var book = new Book(dto.id(), dto.title(),
                dto.languages() == null ? new java.util.HashSet<>() : new java.util.HashSet<>(dto.languages()),
                dto.download_count());

        if (dto.authors() != null) {
            for (var a : dto.authors()) {
                var author = authorRepo.findByNameAndBirthYearAndDeathYear(a.name(), a.birth_year(), a.death_year())
                        .orElseGet(() -> authorRepo.save(new Author(a.name(), a.birth_year(), a.death_year())));
                book.getAuthors().add(author);
            }
        }

        return Optional.of(bookRepo.save(book));
    }

    public List<Book> listarLibros() {
        return bookRepo.findAllWithDetails();
    }

    @Transactional(readOnly = true)
    public List<Author> autoresVivosEn(int year) {
        return authorRepo.findAliveInYearWithBooks(year);
    }

    @Transactional(readOnly = true)
    public List<Author> listarAutoresConLibros() {
        return authorRepo.findAllWithBooksOrderByName();
    }

    public List<Book> librosPorIdioma(String langCode) {
        return bookRepo.findByLanguageWithDetails(langCode);
    }
}

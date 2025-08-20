package com.alura.literalura.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "books", uniqueConstraints = @UniqueConstraint(columnNames = "gutenberg_id"))
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gutenberg_id", nullable = false)
    private Integer gutenbergId;

    @Column(nullable = false)
    private String title;

    @ElementCollection
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "lang_code")
    private Set<String> languages = new HashSet<>();

    @Column(name = "download_count")
    private Integer downloadCount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<Author> authors = new HashSet<>();

    public Book() {
    }

    public Book(Integer gutenbergId, String title, Set<String> languages, Integer downloadCount) {
        this.gutenbergId = gutenbergId;
        this.title = title;
        if (languages != null) this.languages = languages;
        this.downloadCount = downloadCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGutenbergId() {
        return gutenbergId;
    }

    public void setGutenbergId(Integer gutenbergId) {
        this.gutenbergId = gutenbergId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book b)) return false;
        return Objects.equals(gutenbergId, b.gutenbergId);
    }
    @Override public int hashCode() { return Objects.hash(gutenbergId); }
}


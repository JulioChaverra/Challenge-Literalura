package com.alura.literalura.main;

import com.alura.literalura.model.Author;
import com.alura.literalura.model.Book;
import com.alura.literalura.service.BibliotecaService;
import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class MenuLibros {

    private final BibliotecaService service;
    private final Scanner in = new Scanner(System.in);
    private final NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es", "CO"));

    public MenuLibros(BibliotecaService service) {
        this.service = service;
    }

    public void mostrar() {
        int opcion = -1;
        while (opcion != 0) {
            imprimirMenu();
            System.out.print("> ");
            String s = in.nextLine().trim();
            if (s.isEmpty()) continue;
            try { opcion = Integer.parseInt(s); } catch (Exception e) { continue; }

            switch (opcion) {
                case 1 -> buscarPorTitulo();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> autoresVivos();
                case 5 -> librosPorIdioma();
                case 0 -> System.out.println("0 - salir");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarPorTitulo() {
        System.out.println("\nIngrese el nombre del libro que deseas buscar");
        String titulo = in.nextLine().trim();

        try {
            var opt = service.buscarYGuardarPorTitulo(titulo); // puede lanzar IllegalStateException
            if (opt.isPresent()) {
                imprimirLibro(opt.get());
            } else {
                System.out.println("(No se encontraron resultados en Gutendex.)");
            }
        } catch (IllegalStateException e) {
            System.out.println("No se puede agregar el mismo libro");
        }
    }

    private void listarLibros() {
        List<Book> libros = service.listarLibros();
        if (libros.isEmpty()) {
            System.out.println("(No hay libros registrados)");
            return;
        }
        libros.forEach(this::imprimirLibro);
    }

    private void listarAutores() {
        var autores = service.listarAutoresConLibros();
        if (autores.isEmpty()) {
            System.out.println("(No hay autores registrados)");
            return;
        }
        autores.forEach(this::imprimirAutorFormatoCaptura);
    }

    private void imprimirAutorFormatoCaptura(Author a) {
        String nac = a.getBirthYear() == null ? "?" : a.getBirthYear().toString();
        String def = a.getDeathYear() == null ? "?" : a.getDeathYear().toString();

        String libros = a.getBooks() == null || a.getBooks().isEmpty()
                ? "[]"
                : a.getBooks().stream()
                .map(b -> b.getTitle() == null ? "-" : b.getTitle())
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .collect(java.util.stream.Collectors.joining(", ", "[", "]"));

        System.out.println();
        System.out.println("Autor: " + (a.getName() == null ? "-" : a.getName()));
        System.out.println("Fecha de nacimiento: " + nac);
        System.out.println("Fecha de fallecimiento: " + def);
        System.out.println("Libros: " + libros);
    }

    private void autoresVivos() {
        System.out.print("\nIngrese el año: ");
        String s = in.nextLine().trim();
        int year;
        try {
            year = Integer.parseInt(s);
        } catch (Exception e) { System.out.println("Año inválido");
            return;
        }
        var vivos = service.autoresVivosEn(year);
        if (vivos.isEmpty()) {
            System.out.println("(Sin autores vivos en ese año)");
            return;
        }
        vivos.forEach(this::imprimirAutorFormatoCaptura);
    }

    private void librosPorIdioma() {
        System.out.print("\nIngrese el idioma (ej: en, es, fr): ");
        String lang = in.nextLine().trim().toLowerCase();
        List<Book> libros = service.librosPorIdioma(lang);
        if (libros.isEmpty()) {
            System.out.println("(Sin resultados para el idioma: " + lang + ")");
            return;
        }
        libros.forEach(this::imprimirLibro);
    }

    private void imprimirLibro(Book b) {
        String autores = b.getAuthors() == null || b.getAuthors().isEmpty()
                ? "-"
                : b.getAuthors().stream().map(Author::getName).collect(Collectors.joining(", "));

        String idiomas = (b.getLanguages() == null || b.getLanguages().isEmpty())
                ? "-"
                : String.join(", ", b.getLanguages());

        String descargas = b.getDownloadCount() == null ? "-" : nf.format(b.getDownloadCount());

        System.out.println("----- LIBRO -----");
        System.out.println("Título: " + safe(b.getTitle()));
        System.out.println("Autor: " + autores);
        System.out.println("Idioma: " + idiomas);
        System.out.println("Número de descargas: " + descargas);
        System.out.println("-----------------");
    }

    private String safe(String s) { return s == null ? "-" : s; }

    private void imprimirMenu() {
        System.out.println("""
                
                ------------
                Elija la opción a través de su número:
                1 - buscar libro por título
                2 - listar libros registrados
                3 - listar autores registrados
                4 - listar autores vivos en un determinado año
                5 - listar libros por idioma
                0 - salir
                """);
    }
}

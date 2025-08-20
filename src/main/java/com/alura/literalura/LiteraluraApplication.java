package com.alura.literalura;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.alura.literalura.main.MenuLibros;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
    private final MenuLibros menuLibros;
    public LiteraluraApplication(MenuLibros menuLibros) {
        this.menuLibros = menuLibros;
    }
	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        menuLibros.mostrar();
    }
}

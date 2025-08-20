📚 Literalura — CLI con Spring Boot + Gutendex + PostgreSQL

Pequeña app de consola para explorar libros de Gutendex, guardarlos en PostgreSQL y consultarlos desde un menú interactivo. Ideal para practicar Spring Boot 3, WebClient, JPA/Hibernate y consultas derivadas.

✨ Funcionalidades

1 — Buscar libro por título (consulta Gutendex y guarda el 1er resultado)
➜ Si el libro ya existe, muestra: “No se puede agregar el mismo libro”

2 — Listar libros registrados

3 — Listar autores registrados (formato: Nombre, Fechas y Libros: [...])

4 — Listar autores vivos en un año (incluye libros)

5 — Listar libros por idioma (en, es, fr, …)

0 — Salir (cierra la aplicación)

🧱 Stack

Java 17+, Spring Boot 3

WebClient (WebFlux) para llamar a Gutendex

Spring Data JPA + Hibernate

PostgreSQL

Formato de salida bonito en consola (bloques)

⚙️ Configuración rápida
1) Variables de entorno (PostgreSQL)

La app lee estas variables de entorno del sistema:

DB_HOST:localhost

DB_NAME:"tu_database"

DB_PASSWORD:"tu_contraseña"

DB_USER:postgres


# Es una app de consola: no levantes servidor web
spring.main.web-application-type=none


Windows (PowerShell):

setx DB_HOST "localhost"
setx DB_NAME "tu_database"
setx DB_USER "postgres"
setx DB_PASSWORD "tu_password"


Git Bash:

export DB_HOST=localhost
export DB_NAME=tu_database
export DB_USER=postgres
export DB_PASSWORD=tu_password

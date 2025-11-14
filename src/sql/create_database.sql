CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

CREATE TABLE libro (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN DEFAULT FALSE,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(120) NOT NULL,
    editorial VARCHAR(100),
    anio_edicion INT
);

CREATE TABLE ficha_bibliografica (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    eliminado BOOLEAN DEFAULT FALSE,
    isbn VARCHAR(17) UNIQUE,
    clasificacion_dewey VARCHAR(20),
    estanteria VARCHAR(20),
    idioma VARCHAR(30),
    libro_id BIGINT UNIQUE,
    FOREIGN KEY (libro_id) REFERENCES libro(id) ON DELETE CASCADE
);

CREATE INDEX idx_libro_titulo ON libro(titulo);
CREATE INDEX idx_libro_autor ON libro(autor);
CREATE INDEX idx_ficha_isbn ON ficha_bibliografica(isbn);
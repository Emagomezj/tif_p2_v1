/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Emanuel
 * Created: 17 nov 2025
 */

INSERT INTO libro (titulo, autor, editorial, anio_edicion)
    VALUES
        ('Cien años de soledad', 'Gabriel García Márquez', 'Sudamericana', 1967),
        ('1984', 'George Orwell', 'Secker & Warburg', 1949),
        ('El principito', 'Antoine de Saint-Exupéry', 'Reynal & Hitchcock', 1943);
INSERT INTO ficha_bibliografica (isbn, clasificacion_dewey, estanteria, idioma, libro_id)
    VALUES
        ('978-8437604947', '863', 'FIC-001', 'Español', 1),
        ('978-0451524935', '823', 'FIC-002', 'Inglés', 2),
        ('978-0156012195', '843', 'FIC-003', 'Francés', 3);
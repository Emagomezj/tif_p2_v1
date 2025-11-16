# Trabajo Final Integrador – Consigna
## UTN – BibliotecaApp (Libro - Ficha Bibliográfica)

Este proyecto implementa un sistema de gestión de libros utilizando una arquitectura por capas, JDBC, validaciones, transacciones y una relación 1 a 1 entre Libro y FichaBibliografica.


## DOMINIO DEL PROYECTO

El dominio elegido es una Biblioteca.
Cada libro posee una única ficha bibliográfica que contiene información adicional:
ISBN, clasificación Dewey, estantería, idioma.

La relación entre Libro y FichaBibliografica es 1:1.


## ARQUITECTURA DEL PROYECTO

El proyecto está organizado en capas:

BibliotecaApp<br>
├── entities → Modelo del dominio<br>
├── dao → Acceso a datos mediante JDBC<br>
├── service → Reglas de negocio + manejo de transacciones<br>
├── config → Configuración de la conexión a la base de datos<br>
├── utils → Creación automática de la BD + carga de datos iniciales<br>
└── AppMenu → Interfaz de usuario por consola<br>

### Entities:

Libro

FichaBibliografica

### Dao:

GenericDao<T>

LibroDao

FichaBibliograficaDao

### Service:

GenericService<T>

LibroService

### config:

DatabaseConnection

### Utils:

CreateDatabase

InsertData

### UI:

AppMenu


## BASE DE DATOS

Relación 1 a 1:
ficha_bibliografica.libro_id es UNIQUE y FK hacia libro.id.

Se usa:

ON DELETE CASCADE

Índices por título, autor e ISBN


## SCRIPTS SQL REQUERIDOS

La carpeta /sql contiene:

create.sql
Incluye:

CREATE DATABASE

CREATE TABLE libro

CREATE TABLE ficha_bibliografica

FOREIGN KEY

UNIQUE

INDEX

data.sql
Incluye:

INSERT inicial de 3 libros y 3 fichas bibliográficas.


## INSTRUCCIONES DE INSTALACIÓN Y EJECUCIÓN

Clonar el repositorio:

git clone (link de github)

Crear la base de datos ejecutando en MySQL:

SOURCE sql/create.sql;
SOURCE sql/data.sql;

Configurar credenciales en:
/src/main/resources/database.properties

Ejemplo:
db.url=jdbc:mysql://localhost:3306/biblioteca_db
db.user=tu_usuario
db.password=tu_password
db.name=biblioteca_db

Ejecutar la aplicación

Desde NetBeans: Run Project
O por consola:

java -cp "target/classes:mysql-connector-j.jar" utn.tif.AppMenu


## VALIDACIONES IMPLEMENTADAS

Título obligatorio y de máximo 150 caracteres

Autor obligatorio y máximo 120 caracteres

Editorial obligatoria

Año obligatorio y no mayor al actual

ISBN válido (10 o 13 dígitos)

Unicidad del ISBN

Ficha bibliográfica única por libro

Manejo de errores por campos vacíos o inválidos


MANEJO DE TRANSACCIONES

Se utiliza:

conn.setAutoCommit(false)

commit() cuando todo es correcto

rollback() ante cualquier error

Operaciones transaccionales:

Crear libro + ficha

Actualizar libro + ficha

Eliminar libro + ficha (baja lógica)

Esto garantiza consistencia en la BD.


## PRUEBAS REALIZADAS

Crear libro con ficha bibliográfica

Buscar libro por ID

Listar todos los libros

Actualizar libro y su ficha

Eliminar libro (baja lógica)

Buscar por título

Buscar por autor

Intentar crear ficha con ISBN duplicado → rollback correcto

Intentar ingresar año inválido → validación correcta


## DIAGRAMA UML

El diagrama completo se encuentra en el directorio raíz con el nombre de diagrama.png

Incluye:

Relación 1 a 1 (composición)

Realización de interfaces

Dependencias

Paquetes por capas


## VIDEO DEL TFI

(enlace)

---

## AUTORES
- Matías Farfán
- Emanuel Gómez Juárez
- Emiliza Gómez Juárez
- Marianela Guerrero

Programación II
Tecnicatura Universitaria en Programación
UTN – Buenos Aires
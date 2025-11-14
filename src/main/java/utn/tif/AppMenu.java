package utn.tif;
import utn.tif.entities.Libro;
import utn.tif.entities.FichaBibliografica;
import utn.tif.service.LibroService;
import utn.tif.utils.CreateDatabase;
import utn.tif.utils.InsertData;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AppMenu {
    private LibroService libroService;
    private Scanner scanner;

    public AppMenu() {
        this.libroService = new LibroService();
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        if(!CreateDatabase.checkearExistencia()) {
            CreateDatabase.crearBaseDeDatos();
        } else{
            System.out.println("La base de datos ya existe, no se crea nuevamente");
        }
        InsertData.insertarDatos();
        while (true) {
            System.out.println("\nTrabajo Final Integrador: Libro - Ficha Bibliografica");
            System.out.println("1. Crear libro");
            System.out.println("2. Buscar libro por ID");
            System.out.println("3. Listar todos los libros");
            System.out.println("4. Actualizar libro");
            System.out.println("5. Eliminar libro");
            System.out.println("6. Buscar por título");
            System.out.println("7. Buscar por autor");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                int opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        crearLibro();
                        break;
                    case 2:
                        buscarLibroPorId();
                        break;
                    case 3:
                        listarLibros();
                        break;
                    case 4:
                        actualizarLibro();
                        break;
                    case 5:
                        eliminarLibro();
                        break;
                    case 6:
                        buscarPorTitulo();
                        break;
                    case 7:
                        buscarPorAutor();
                        break;
                    case 0:
                        System.out.println("¡Hasta luego!");
                        return;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void crearLibro() throws Exception {
        System.out.println("\nCREAR NUEVO LIBRO");

        Libro libro = new Libro();

        System.out.print("Título: ");
        libro.setTitulo(scanner.nextLine());

        System.out.print("Autor: ");
        libro.setAutor(scanner.nextLine());

        System.out.print("Editorial: ");
        libro.setEditorial(scanner.nextLine());

        System.out.print("Año de edición: ");
        String anioStr = scanner.nextLine();
        if (!anioStr.isEmpty()) {
            libro.setAnioEdicion(Integer.parseInt(anioStr));
        }

        FichaBibliografica ficha = new FichaBibliografica();

        System.out.print("ISBN: ");
        ficha.setIsbn(scanner.nextLine());

        System.out.print("Clasificación Dewey: ");
        ficha.setClasificacionDewey(scanner.nextLine());

        System.out.print("Estantería: ");
        ficha.setEstanteria(scanner.nextLine());

        System.out.print("Idioma: ");
        ficha.setIdioma(scanner.nextLine());

        libro.setFichaBibliografica(ficha);

        Libro libroCreado = libroService.insertar(libro);
        System.out.println("Libro creado exitosamente: " + libroCreado);
    }

    private void buscarLibroPorId() throws Exception {
        System.out.print("Ingrese ID del libro: ");
        Long id = Long.parseLong(scanner.nextLine());

        Libro libro = libroService.getById(id);
        if (libro != null) {
            System.out.println("Libro encontrado: " + libro);
            if (libro.getFichaBibliografica() != null) {
                System.out.println("   " + libro.getFichaBibliografica());
            }
        } else {
            System.out.println("No se encontró ningún libro con ID: " + id);
        }
    }

    private void listarLibros() throws Exception {
        List<Libro> libros = libroService.getAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados");
        } else {
            System.out.println("\nLISTA DE LIBROS");
            for (Libro libro : libros) {
                System.out.println(libro);
                if (libro.getFichaBibliografica() != null) {
                    System.out.println("   " + libro.getFichaBibliografica());
                }
                System.out.println();
            }
        }
    }

    private void actualizarLibro() throws Exception {
        System.out.print("Ingrese ID del libro a actualizar: ");
        Long id = Long.parseLong(scanner.nextLine());

        Libro libroExistente = libroService.getById(id);
        if (libroExistente == null) {
            System.out.println("No se encontró ningún libro con ID: " + id);
            return;
        }

        System.out.println("Libro actual: " + libroExistente);

        System.out.print("Nuevo título (" + libroExistente.getTitulo() + "): ");
        String titulo = scanner.nextLine();
        if (!titulo.isEmpty()) {
            libroExistente.setTitulo(titulo);
        }

        System.out.print("Nuevo autor (" + libroExistente.getAutor() + "): ");
        String autor = scanner.nextLine();
        if (!autor.isEmpty()) {
            libroExistente.setAutor(autor);
        }

        System.out.print("Nueva editorial (" + libroExistente.getEditorial() + "): ");
        String editorial = scanner.nextLine();
        if (!editorial.isEmpty()) {
            libroExistente.setEditorial(editorial);
        }

        System.out.print("Nuevo año de edición (" + libroExistente.getAnioEdicion() + "): ");
        String anioStr = scanner.nextLine();
        if (!anioStr.isEmpty()) {
            libroExistente.setAnioEdicion(Integer.parseInt(anioStr));
        }


        if (libroExistente.getFichaBibliografica() != null) {
            System.out.println("Ficha actual: " + libroExistente.getFichaBibliografica());
            String respuesta = null;
            do {
                System.out.print("¿Desea actualizar la ficha bibliográfica? (S/N): ");
                respuesta = scanner.nextLine().trim().toUpperCase();
            } while (!respuesta.equals("S") && !respuesta.equals("N"));

            if (respuesta.equals("S")) {
                FichaBibliografica ficha = libroExistente.getFichaBibliografica();

                System.out.print("Nuevo ISBN (" + ficha.getIsbn() + "): ");
                String isbn = scanner.nextLine();
                if (!isbn.isEmpty()) {
                    ficha.setIsbn(isbn);
                }

                System.out.print("Nueva clasificación Dewey (" + ficha.getClasificacionDewey() + "): ");
                String dewey = scanner.nextLine();
                if (!dewey.isEmpty()) {
                    ficha.setClasificacionDewey(dewey);
                }

                System.out.print("Nueva estantería (" + ficha.getEstanteria() + "): ");
                String estanteria = scanner.nextLine();
                if (!estanteria.isEmpty()) {
                    ficha.setEstanteria(estanteria);
                }

                System.out.print("Nuevo idioma (" + ficha.getIdioma() + "): ");
                String idioma = scanner.nextLine();
                if (!idioma.isEmpty()) {
                    ficha.setIdioma(idioma);
                }
            }
        }

        Libro libroActualizado = libroService.actualizar(libroExistente);
        System.out.println("Libro actualizado exitosamente: \n" + libroActualizado);
        System.out.println(libroActualizado.getFichaBibliografica());
    }

    private void eliminarLibro() throws Exception {
        System.out.print("Ingrese ID del libro a eliminar: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("¿Está seguro de que desea eliminar el libro? (S/N): ");
        String confirmacion = scanner.nextLine().toUpperCase();

        if (confirmacion.equals("S")) {
            libroService.eliminar(id);
            System.out.println("Libro eliminado exitosamente");
        } else {
            System.out.println("Eliminación cancelada");
        }
    }

    private void buscarPorTitulo() throws Exception {
        System.out.print("Ingrese título a buscar: ");
        String titulo = scanner.nextLine();

        Optional<?> libros = libroService.buscarPorTitulo(titulo);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros con título: " + titulo);
        } else {
            System.out.println("\nRESULTADOS DE LA BÚSQUEDA");
            Object l = libros.get();
            if (l instanceof String) {
                System.out.println(l);
            } else {
                List<Libro> lista = (List<Libro>) l;
                for (Libro libro : lista) {
                    System.out.println(libro);
                }
            }
        }
    }

    private void buscarPorAutor() throws Exception {
        System.out.print("Ingrese autor a buscar: ");
        String autor = scanner.nextLine();

        Optional<?> libros = libroService.buscarPorAutor(autor);

        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros del autor: " + autor);
        } else {
            System.out.println("\nRESULTADOS DE LA BÚSQUEDA");
            Object libro = libros.get();
            if(libro instanceof String){
                System.out.println(libro);
            } else {
                List<Libro> lista = (List<Libro>) libro;
                for (Libro l : lista) {
                    System.out.println(l);
                }
            }
        }
    }

    public static void main(String[] args) {
        AppMenu menu = new AppMenu();
        menu.mostrarMenu();
    }
}
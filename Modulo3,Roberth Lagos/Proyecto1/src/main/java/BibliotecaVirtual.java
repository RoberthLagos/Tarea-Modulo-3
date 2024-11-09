import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BibliotecaVirtual {

    // Clase Libro: Representa un libro en la biblioteca
    static class Libro {     
        private String Titulo;
        private String Autor;
        private int AnioPublicacion;
        private String Genero;
        private boolean Disponible;

        //Constructor clase del Libro
        public Libro(String Titulo, String Autor, int AnioPublicacion, String Genero) {
            this.Titulo = Titulo;
            this.Autor = Autor;
            this.AnioPublicacion = AnioPublicacion;
            this.Genero = Genero;
            this.Disponible = true; 
        }

        // Métodos getters
        public String getTitulo() {
            return Titulo;
        }

        public String getAutor() {
            return Autor;
        }

        public boolean isDisponible() {
            return Disponible;
        }

        // Método para prestar el libro
        public void prestar() {
            this.Disponible = false;
        }

        // Método para devolver el libro
        public void devolver() {
            this.Disponible = true;
        }

        // Representacion del libro
        @Override
        public String toString() {
            return "Título: " + Titulo + ", Autor: " + Autor + ", Año: " + AnioPublicacion + ", Genero: " + Genero + ", Disponible: " + (Disponible ? "Sí" : "No");
        }
    }

    // Clase Biblioteca: Maneja la colección de libros
    static class Biblioteca {
        private ArrayList<Libro> libros;

        // Constructor de la clase Biblioteca
        public Biblioteca() {
            libros = new ArrayList<>();
        }

        // Métodos:
        public void agregarLibro(Libro libro) {
            libros.add(libro);
        }

        
        public ArrayList<Libro> buscarPorTitulo(String titulo) {
            ArrayList<Libro> resultados = new ArrayList<>();
            for (Libro libro : libros) {
                if (libro.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                    resultados.add(libro);
                }
            }
            return resultados;
        }

       
        public boolean borrarLibro(String titulo) {
            return libros.removeIf(libro -> libro.getTitulo().equalsIgnoreCase(titulo));
        }

       
        public boolean prestarLibro(Libro libro) {
            if (libro.isDisponible()) {
                libro.prestar();
                return true;
            }
            return false;
        }

        
        public boolean devolverLibro(Libro libro) {
            if (!libro.isDisponible()) {
                libro.devolver();
                return true;
            }
            return false;
        }

        
        public ArrayList<Libro> getLibrosDisponibles() {
            ArrayList<Libro> disponibles = new ArrayList<>();
            for (Libro libro : libros) {
                if (libro.isDisponible()) {
                    disponibles.add(libro);
                }
            }
            return disponibles;
        }
    }

    // Método de entrada.
    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        crearVentana(biblioteca); // Crear la interfaz gráfica
    }

    // Método para crear y configurar la ventana de la aplicación
    private static void crearVentana(Biblioteca biblioteca) {
        // Configuración básica de la ventana
        JFrame frame = new JFrame("Biblioteca Virtual");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new BorderLayout());

        // Creación del panel principal con diseño )
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.setBackground(Color.LIGHT_GRAY); // Cambia el color de fondo

        // Texto para ingresar título y autor
        JTextField tituloField = new JTextField();
        JTextField autorField = new JTextField();

        // Texto para mostrar resultados y mensajes
        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setBackground(Color.LIGHT_GRAY); 

        // Botones para las operaciones
        JButton agregarButton = new JButton("Agregar Libro");
        JButton buscarButton = new JButton("Buscar Libro por Título");
        JButton prestarButton = new JButton("Prestar Libro");
        JButton devolverButton = new JButton("Devolver Libro");
        JButton mostrarButton = new JButton("Mostrar Libros Disponibles");
        JButton borrarButton = new JButton("Borrar Libro");
        JButton limpiarButton = new JButton("Limpiar Pantalla"); 

        // Añadir componentes al panel con etiquetas descriptivas
        panel.add(new JLabel("Título:"));
        panel.add(tituloField);
        panel.add(new JLabel("Autor:"));
        panel.add(autorField);
        panel.add(agregarButton);
        panel.add(buscarButton);
        panel.add(prestarButton);
        panel.add(devolverButton);
        panel.add(borrarButton); 
        panel.add(mostrarButton);
        panel.add(limpiarButton); 
        panel.add(new JScrollPane(resultadoArea)); 

        // Añadir el panel a la ventana principal
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true); 

        // Acción para agregar libro
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText().trim(); 
                String autor = autorField.getText().trim();   

                // Validar que los campos no estén vacíos
                if(titulo.isEmpty() || autor.isEmpty()) {
                    resultadoArea.append("Error: Título y Autor no pueden estar vacíos.\n");
                    return;
                }

                // Crear un nuevo libro y agregarlo a la biblioteca
                Libro nuevoLibro = new Libro(titulo, autor, 2024, "Ficción");
                biblioteca.agregarLibro(nuevoLibro);
                resultadoArea.append("Libro agregado: " + nuevoLibro + "\n");

                // Limpiar los campos de texto después de agregar
                tituloField.setText("");
                autorField.setText("");
            }
        });

        // Acción para buscar libro por título
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText().trim(); 

                // Validar que el campo de titulo no este vacio
                if(titulo.isEmpty()) {
                    resultadoArea.append("Error: Ingrese un título para buscar.\n");
                    return;
                }

                // Buscar libros que coincidan con el título
                var resultados = biblioteca.buscarPorTitulo(titulo);
                if(resultados.isEmpty()) {
                    resultadoArea.append("No se encontraron libros con el título: " + titulo + "\n");
                } else {
                    resultadoArea.append("Resultados de búsqueda:\n");
                    for (Libro libro : resultados) {
                        resultadoArea.append(libro + "\n");
                    }
                }

                
                tituloField.setText("");
            }
        });

        // Acción para prestar un libro
        prestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText().trim(); // Obtener y limpiar el texto del título

                // Validar que el campo de título no esté vacío
                if(titulo.isEmpty()) {
                    resultadoArea.append("Error: Ingrese un título para prestar.\n");
                    return;
                }

                // Busqueda del libro por titulo
                ArrayList<Libro> resultados = biblioteca.buscarPorTitulo(titulo);
                if (!resultados.isEmpty()) {
                    Libro libro = resultados.get(0); 
                    if (biblioteca.prestarLibro(libro)) {
                        resultadoArea.append("Libro prestado: " + libro + "\n");
                    } else {
                        resultadoArea.append("El libro ya está prestado.\n");
                    }
                } else {
                    resultadoArea.append("No se encontró el libro.\n");
                }

                
                tituloField.setText("");
            }
        });

        // Devolver un libro
        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText().trim(); 

                // Validar que el campo de título no esté vacío
                if(titulo.isEmpty()) {
                    resultadoArea.append("Error: Ingrese un título para devolver.\n");
                    return;
                }

                // Buscar el libro por título
                ArrayList<Libro> resultados = biblioteca.buscarPorTitulo(titulo);
                if (!resultados.isEmpty()) {
                    Libro libro = resultados.get(0); 
                    if (biblioteca.devolverLibro(libro)) {
                        resultadoArea.append("Libro devuelto: " + libro + "\n");
                    } else {
                        resultadoArea.append("El libro no está prestado.\n");
                    }
                } else {
                    resultadoArea.append("No se encontró el libro.\n");
                }

                
                tituloField.setText("");
            }
        });

        // Borrar un libro
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText().trim(); 

                
                if(titulo.isEmpty()) {
                    resultadoArea.append("Error: Ingrese un título para borrar.\n");
                    return;
                }

                //Borrar el libro de la biblioteca
                if (biblioteca.borrarLibro(titulo)) {
                    resultadoArea.append("Libro borrado: " + titulo + "\n");
                } else {
                    resultadoArea.append("No se encontró el libro para borrar.\n");
                }

                
                tituloField.setText("");
            }
        });

        //Mostrar todos los libros disponibles
        mostrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Libro> disponibles = biblioteca.getLibrosDisponibles();
                if(disponibles.isEmpty()) {
                    resultadoArea.append("No hay libros disponibles.\n");
                } else {
                    resultadoArea.append("Libros disponibles:\n");
                    for (Libro libro : disponibles) {
                        resultadoArea.append(libro + "\n");
                    }
                }
            }
        });

        //Limpiar las pantallas
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tituloField.setText("");     
                autorField.setText("");       
                resultadoArea.setText("");  
                resultadoArea.append("Pantalla limpiada.\n"); 
            }
        });
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectotfi.config;

/**
 *
 * @author rodrigo_aguirre
 */
// Importamos la libreri­a dotenv para leer variables desde el archivo .env
import io.github.cdimascio.dotenv.Dotenv;
// Importamos las clases necesarias para la conexion a la base de datos
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Clase responsable de establecer la conexion con la base de datos MySQL.
 *
 * Usa la libreri­a dotenv para obtener los datos de conexion desde un archivo
 * .env ubicado en el directorio rai­z del proyecto.
 */
public class DatabaseConnection {

    // Carga automaticamente las variables definidas en el archivo .env
    private static final Dotenv dotenv = Dotenv.configure()
        .directory("src/proyectotfi/config") // ruta relativa desde la raiz del proyecto
        .filename(".env")                    // nombre del archivo
        .load();

    // Variables obtenidas desde el archivo .env
    public static String host = dotenv.get("DB_HOST");
    public static String port = dotenv.get("DB_PORT");
    public static String dbName = dotenv.get("DB_NAME");
    private static String user = dotenv.get("DB_USER");
    private static String password = dotenv.get("DB_PASSWORD");

    // Construimos dinamicamente la URL de conexion usando los valores del .env
    private static String server_url = String.format("jdbc:mysql://%s:%s/",host, port);
    private static String db_url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC",host, port, dbName);

    public static Connection getConnection() {
        try {
            // Intenta establecer la conexion usando los parametros cargados
            Connection con = DriverManager.getConnection(db_url, user, password);
            System.out.println("Conexion exitosa a la base de datos.");
            return con;
        } catch (SQLException e) {
            // Si ocurre un error, lanzamos una excepcion
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }
    
    public static Connection getServerConnection() {
    try {
        // Intenta establecer la conexion usando los parametros cargados
        Connection con = DriverManager.getConnection(server_url, user, password);
        System.out.println("Conexion exitosa a la base de datos.");
        return con;
    } catch (SQLException e) {
        // Si ocurre un error, lanzamos una excepcion
        throw new RuntimeException("Error al conectar con la base de datos", e);
    }
    }
}


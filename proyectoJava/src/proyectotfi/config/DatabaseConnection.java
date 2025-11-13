/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectotfi.config;

/**
 *
 * @author rodrigo_aguirre
 */

 // Importamos la librería dotenv para leer variables desde el archivo .env
import io.github.cdimascio.dotenv.Dotenv;
// Importamos las clases necesarias para la conexión a la base de datos
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase responsable de establecer la conexión con la base de datos MySQL.
 * 
 * Usa la librería dotenv para obtener los datos de conexión desde un archivo .env
 * ubicado en el directorio raíz del proyecto.
 */

public class DatabaseConnection {

    // Carga automáticamente las variables definidas en el archivo .env
    private static final Dotenv dotenv = Dotenv.load();

    // Variables obtenidas desde el archivo .env
    private static String host = dotenv.get("DB_HOST");
    private static String port = dotenv.get("DB_PORT");
    private static String dbName = dotenv.get("DB_NAME");
    private static String user = dotenv.get("DB_USER");
    private static String password = dotenv.get("DB_PASSWORD");

    // Construimos dinámicamente la URL de conexión usando los valores del .env
    private static String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC",host, port, dbName);

    public static Connection getConnection() {
        try {
            // Intenta establecer la conexión usando los parámetros cargados
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            // Si ocurre un error, lanzamos una excepción
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }
}
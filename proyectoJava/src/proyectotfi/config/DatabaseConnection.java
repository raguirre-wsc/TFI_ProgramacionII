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
import java.sql.*;

/**
 * Clase responsable de establecer la conexion con la base de datos MySQL.
 *
 * Usa la libreri­a dotenv para obtener los datos de conexion desde un archivo
 * .env ubicado en el directorio rai­z del proyecto.
 */
public class DatabaseConnection {

    public static Connection getConnection() {
            // Carga automaticamente las variables definidas en el archivo .env
    Dotenv dotenv = Dotenv.configure()
            .directory("src/proyectotfi/config") // ruta relativa desde la raiz del proyecto
            .filename(".env") // nombre del archivo
            .load();

        // Variables obtenidas desde el archivo .env
        String host = dotenv.get("DB_HOST");
        String port = dotenv.get("DB_PORT");
        String dbName = dotenv.get("DB_NAME");
        String user = dotenv.get("DB_USER");
        String pass = dotenv.get("DB_PASSWORD");

        try {
            // Primero se conecta sin elegir base de datos
            String urlSinDB = "jdbc:mysql://" + host + ":" + port + "/";
            Connection conn = DriverManager.getConnection(urlSinDB, user, pass);

            // Crea base si no existe
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE DATABASE IF NOT EXISTS " + dbName);
            }

            conn.close();

            // Conectarse a la base creada
            String urlConDB = "jdbc:mysql://" + host + ":" + port + "/" + dbName
                    + "?useSSL=false&serverTimezone=UTC";
            return DriverManager.getConnection(urlConDB, user, pass);

        } catch (Exception e) {
            throw new RuntimeException("Error al conectar con la base de datos", e);
        }
    }
}


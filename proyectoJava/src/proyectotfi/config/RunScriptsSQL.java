package proyectotfi.config;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class RunScriptsSQL {
    public static void main(String[] args) {
        // Rutas relativas a los scripts SQL del proyecto
        String script1 = "src/proyectotfi/config/01_esquema.sql";
        String script2 = "src/proyectotfi/config/03_carga_masiva.sql";
        
        // Utilizamos la clasa DatabaseConnection para obtener una conexion a la DB"
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Ejecutamos script de cracion de esquema "Pedido_Envio"
            String sql = new String(Files.readAllBytes(Paths.get(script1)));
            for (String query : sql.split(";")) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query);
                }
            }
            System.out.println("El esquema se creo exitosamente.");
            
            // Realizamos carga masiva de datos
            String sql2 = new String(Files.readAllBytes(Paths.get(script2)));
            for (String query : sql2.split(";")) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query);
                }
            }
            System.out.println("Se realizo carga masiva de datos exitosamente.");
System.out.println(conn.getMetaData().getURL());
        } catch (Exception e) {
            e.printStackTrace();    
            System.out.println("Se produjo un error al correr Script SQL.");
        }
    }
}
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
        
        // Utilizamos la clasa DatabaseConnection para obtener una conexion a al servido MySQL para poder crear la BD"
        try (Connection conn = DatabaseConnection.getServerConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE DATABASE IF NOT EXISTS " + DatabaseConnection.dbName);
            System.out.println("Se creo la base de datos correctamente.");}
            
        catch (Exception e) {
            e.printStackTrace();    
            System.out.println("Se produjo un error al crear ls BD.");
        }  
        
        // Utilizamos la clasa DatabaseConnection para obtener una conexion a la DB y crear el esquema"
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Ejecutamos script de cracion de esquema "Pedido_Envio"
            String sql = new String(Files.readAllBytes(Paths.get(script1)));
            for (String query : sql.split(";")) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query);
                }
            }
            System.out.println("El esquema se creo correctamente.");}
            
        catch (Exception e) {
            e.printStackTrace();    
            System.out.println("Se produjo un error al crear el esquema.");
        }    
        
        // Utilizamos la clasa DatabaseConnection para obtener una conexion a la DB y poblar datos"        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            // Realizamos carga masiva de datos
            String sql2 = new String(Files.readAllBytes(Paths.get(script2)));
            for (String query : sql2.split(";")) {
                if (!query.trim().isEmpty()) {
                    stmt.execute(query);
                }
            }
            System.out.println("Se realizo carga masiva de datos correctamente.");}
        
        catch (Exception e) {
            e.printStackTrace();    
            System.out.println("Se produjo un error en la carga masiva.");
        }   
            

    }
}
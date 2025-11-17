/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectotfi.main;

import proyectotfi.service.PedidoService;
import proyectotfi.service.EnvioService;
import proyectotfi.entities.Pedido;
import proyectotfi.entities.Envio;
import proyectotfi.entities.enums.EstadoPedido;
import proyectotfi.entities.enums.EstadoEnvio;
import proyectotfi.entities.enums.Empresa;
import proyectotfi.entities.enums.TipoEnvio;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author solyo
 */
public class AppMenu {

    private final Scanner scanner = new Scanner(System.in);
    private final PedidoService pedidoService = new PedidoService();
    private final EnvioService envioService = new EnvioService();

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Elegí una opción: ");
            switch (opcion) {
                case 1 -> menuPedidos();
                case 2 -> menuEnvios();
                case 0 -> System.out.println("Saliendo. ¡Chau!");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. CRUD de Pedidos");
        System.out.println("2. CRUD de Envíos");
        System.out.println("0. Salir");
    }

    // ---------------- PEDIDOS ----------------
    private void menuPedidos() {
        int opcion;
        do {
            System.out.println("\n----- CRUD PEDIDO -----");
            System.out.println("1. Crear Pedido");
            System.out.println("2. Ver Pedido por ID");
            System.out.println("3. Listar Pedidos");
            System.out.println("4. Actualizar Pedido");
            System.out.println("5. Eliminar Pedido (baja lógica)");
            System.out.println("6. Buscar por número");
            System.out.println("0. Volver");
            opcion = leerEntero("Elegí una opción: ");
            switch (opcion) {
                case 1 -> crearPedido();
                case 2 -> verPedidoPorId();
                case 3 -> listarPedidos();
                case 4 -> actualizarPedido();
                case 5 -> eliminarPedido();
                case 6 -> buscarPedidoPorNumero();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearPedido() {
        System.out.println("\n[Crear Pedido]");
        try {
            String numero = leerTextoNoVacio("Número de pedido: ");
            LocalDate fecha = leerFecha("Fecha (AAAA-MM-DD): ");
            String cliente = leerTextoNoVacio("Nombre del cliente: ");
            double total = leerDouble("Total: ");

            // estado por defecto NUEVO
            EstadoPedido estado = EstadoPedido.NUEVO;

            // Preguntar si crear envío asociado
            System.out.print("¿Crear envío asociado ahora? (S/N): ");
            String r = scanner.nextLine().trim().toUpperCase();
            Envio envio = null;
            if (r.equals("S")) {
                envio = crearEnvioInteractivoObject();
            } else {
                System.out.println("Atención: el Pedido requiere Envío según las validaciones del Service. Si falla, el Service lanzará un error.");
            }

            Pedido p = new Pedido(null, false, numero, fecha, cliente, total, estado, envio);

            pedidoService.insertar(p); // llama a la implementación real
            System.out.println("Pedido creado con éxito. ID: " + p.getId());
        } catch (Exception e) {
            System.out.println("Error al crear pedido: " + e.getMessage());
        }
    }

    private void verPedidoPorId() {
        long id = leerLong("ID del pedido: ");
        try {
            Pedido p = pedidoService.getById(id);
            if (p == null) System.out.println("No existe pedido con ese ID o está eliminado.");
            else System.out.println(p);
        } catch (Exception e) {
            System.out.println("Error al obtener pedido: " + e.getMessage());
        }
    }

    private void listarPedidos() {
        try {
            List<Pedido> lista = pedidoService.getAll();
            if (lista == null || lista.isEmpty()) {
                System.out.println("No hay pedidos para mostrar.");
                return;
            }
            lista.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
    }

    private void actualizarPedido() {
        long id = leerLong("ID del pedido a actualizar: ");
        try {
            Pedido p = pedidoService.getById(id);
            if (p == null) {
                System.out.println("No existe pedido con ese ID.");
                return;
            }
            System.out.println("Dejar vacío para no cambiar.");
            String numero = leerTexto("Número (" + p.getNumero() + "): ");
            if (!numero.isBlank()) p.setNumero(numero);
            String fechaStr = leerTexto("Fecha (" + p.getFecha() + " - AAAA-MM-DD): ");
            if (!fechaStr.isBlank()) p.setFecha(LocalDate.parse(fechaStr));
            String cliente = leerTexto("Cliente (" + p.getClienteNombre() + "): ");
            if (!cliente.isBlank()) p.setClienteNombre(cliente);
            String totalStr = leerTexto("Total (" + p.getTotal() + "): ");
            if (!totalStr.isBlank()) p.setTotal(Double.parseDouble(totalStr));

            // Actualizar envio si usuario quiere
            System.out.print("¿Actualizar datos del envío asociado? (S/N): ");
            String r = scanner.nextLine().trim().toUpperCase();
            if (r.equals("S") && p.getEnvio() != null) {
                actualizarEnvioInteractivo(p.getEnvio());
            }

            pedidoService.actualizar(p);
            System.out.println("Pedido actualizado.");
        } catch (Exception e) {
            System.out.println("Error al actualizar pedido: " + e.getMessage());
        }
    }

    private void eliminarPedido() {
        long id = leerLong("ID del pedido a eliminar: ");
        try {
            pedidoService.eliminar(id);
            System.out.println("Pedido marcado como eliminado.");
        } catch (Exception e) {
            System.out.println("Error al eliminar pedido: " + e.getMessage());
        }
    }

    private void buscarPedidoPorNumero() {
        String numero = leerTextoNoVacio("Número de pedido: ");
        try {
            // Si el Service no implementa buscarPorNumero, buscamos en getAll()
            List<Pedido> lista = pedidoService.getAll();
            if (lista == null) {
                System.out.println("No hay pedidos.");
                return;
            }
            boolean found = false;
            for (Pedido p : lista) {
                if (numero.equals(p.getNumero())) {
                    System.out.println(p);
                    found = true;
                }
            }
            if (!found) System.out.println("No se encontró pedido con ese número.");
        } catch (Exception e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
    }

    // ---------------- ENVIOS ----------------
    private void menuEnvios() {
        int opcion;
        do {
            System.out.println("\n----- CRUD ENVÍO -----");
            System.out.println("1. Crear Envío");
            System.out.println("2. Ver Envío por ID");
            System.out.println("3. Listar Envíos");
            System.out.println("4. Actualizar Envío");
            System.out.println("5. Eliminar Envío (baja lógica)");
            System.out.println("0. Volver");
            opcion = leerEntero("Elegí una opción: ");
            switch (opcion) {
                case 1 -> crearEnvioMenu();
                case 2 -> verEnvioPorId();
                case 3 -> listarEnvios();
                case 4 -> actualizarEnvio();
                case 5 -> eliminarEnvio();
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private void crearEnvioMenu() {
        System.out.println("\n[Crear Envío]");
        try {
            Envio e = crearEnvioInteractivoObject();
            envioService.insertar(e);
            System.out.println("Envío creado. ID: " + e.getId());
        } catch (Exception ex) {
            System.out.println("Error al crear envío: " + ex.getMessage());
        }
    }

    private Envio crearEnvioInteractivoObject() {
        String tracking = leerTextoNoVacio("Tracking: ");
        Empresa empresa = readEmpresa();
        TipoEnvio tipo = readTipoEnvio();
        double costo = leerDouble("Costo: ");
        LocalDate fechaDespacho = leerFecha("Fecha despacho (AAAA-MM-DD): ");
        LocalDate fechaEstimada = leerFecha("Fecha estimada (AAAA-MM-DD): ");
        EstadoEnvio estado = EstadoEnvio.EN_PREPARACION;

        return new Envio(null, false, tracking, empresa, tipo, costo, fechaDespacho, fechaEstimada, estado);
    }

    private void verEnvioPorId() {
        long id = leerLong("ID del envío: ");
        try {
            Envio e = envioService.getById(id);
            if (e == null) System.out.println("No existe envío con ese ID o está eliminado.");
            else System.out.println(e);
        } catch (Exception ex) {
            System.out.println("Error al obtener envío: " + ex.getMessage());
        }
    }

    private void listarEnvios() {
        try {
            List<Envio> lista = envioService.getAll();
            if (lista == null || lista.isEmpty()) {
                System.out.println("No hay envíos para mostrar.");
                return;
            }
            lista.forEach(System.out::println);
        } catch (Exception ex) {
            System.out.println("Error al listar envíos: " + ex.getMessage());
        }
    }

    private void actualizarEnvio() {
        long id = leerLong("ID del envío a actualizar: ");
        try {
            Envio e = envioService.getById(id);
            if (e == null) {
                System.out.println("No existe envío con ese ID.");
                return;
            }
            actualizarEnvioInteractivo(e);
            envioService.actualizar(e);
            System.out.println("Envío actualizado.");
        } catch (Exception ex) {
            System.out.println("Error al actualizar envío: " + ex.getMessage());
        }
    }

    private void eliminarEnvio() {
        long id = leerLong("ID del envío a eliminar: ");
        try {
            envioService.eliminar(id);
            System.out.println("Envío marcado como eliminado.");
        } catch (Exception ex) {
            System.out.println("Error al eliminar envío: " + ex.getMessage());
        }
    }

    // Actualiza campos de Envio (interactivo)
    private void actualizarEnvioInteractivo(Envio e) {
        System.out.println("Dejar vacío para no cambiar.");
        String track = leerTexto("Tracking (" + e.getTracking() + "): ");
        if (!track.isBlank()) e.setTracking(track);

        System.out.print("Empresa (" + (e.getEmpresa() != null ? e.getEmpresa() : "") + ") [ANDREANI/OCA/CORREO_ARG]: ");
        String emp = scanner.nextLine().trim();
        if (!emp.isBlank()) e.setEmpresa(Empresa.valueOf(emp.toUpperCase()));

        System.out.print("Tipo (" + (e.getTipo() != null ? e.getTipo() : "") + ") [ESTANDAR/EXPRES]: ");
        String t = scanner.nextLine().trim();
        if (!t.isBlank()) e.setTipo(TipoEnvio.valueOf(t.toUpperCase()));

        String costo = leerTexto("Costo (" + e.getCosto() + "): ");
        if (!costo.isBlank()) e.setCosto(Double.parseDouble(costo));

        System.out.print("Fecha despacho (" + e.getFechaDespacho() + " - AAAA-MM-DD): ");
        String fDesp = scanner.nextLine().trim();
        if (!fDesp.isBlank()) e.setFechaDespacho(LocalDate.parse(fDesp));

        System.out.print("Fecha estimada (" + e.getFechaEstimada() + " - AAAA-MM-DD): ");
        String fEst = scanner.nextLine().trim();
        if (!fEst.isBlank()) e.setFechaEstimada(LocalDate.parse(fEst));

        System.out.print("Estado (" + (e.getEstado()!=null?e.getEstado():"") + ") [EN_PREPARACION/EN_TRANSITO/ENTREGADO]: ");
        String est = scanner.nextLine().trim();
        if (!est.isBlank()) e.setEstado(EstadoEnvio.valueOf(est.toUpperCase()));
    }

    // ---------------- HELPERS / LECTURA ----------------
    private int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Ingresá un número entero válido.");
            }
        }
    }

    private long leerLong(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("ID inválido. Ingresá un número entero.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Ingresá un número decimal válido.");
            }
        }
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException ex) {
                System.out.println("Fecha inválida. Usá el formato AAAA-MM-DD.");
            }
        }
    }

    private String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine();
    }

    private String leerTextoNoVacio(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String s = scanner.nextLine();
            if (!s.isBlank()) return s;
            System.out.println("El campo no puede estar vacío.");
        }
    }

    private Empresa readEmpresa() {
        while (true) {
            System.out.print("Empresa (ANDREANI / OCA / CORREO_ARG): ");
            String s = scanner.nextLine().trim().toUpperCase();
            try {
                return Empresa.valueOf(s);
            } catch (Exception ex) {
                System.out.println("Empresa inválida. Probá ANDREANI, OCA o CORREO_ARG.");
            }
        }
    }

    private TipoEnvio readTipoEnvio() {
        while (true) {
            System.out.print("Tipo (ESTANDAR / EXPRES): ");
            String s = scanner.nextLine().trim().toUpperCase();
            try {
                return TipoEnvio.valueOf(s);
            } catch (Exception ex) {
                System.out.println("Tipo inválido. Probá ESTANDAR o EXPRES.");
            }
        }
    }
}

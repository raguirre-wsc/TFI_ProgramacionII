# Trabajo Final Integrador – Programación 2  
## Aplicación Java con relación 1→1 unidireccional + JDBC + DAO + MySQL  
### UTN – Tecnicatura en Programación

---

## Integrantes y Roles
| Integrante | Rol |
|-----------|------|
| Aguirre Rodrigo | Diseño UML, entidades y conexión SQL |
| Sol Yoon | Implementación de la capa DAO y consultas SQL |
| Celeste Monsalbe | Capa Service, validaciones y manejo de transacciones |
| Magdalena Darchez | Desarrollo del menú, pruebas funcionales y documentación |

---

## Descripción del dominio  
El sistema modela un gestor de Pedidos, donde cada Pedido posee exactamente un Envío asociado.  
La relación es 1→1 unidireccional, es decir:

```
Pedido → Envio
```

El Pedido conoce a su Envio, pero el Envio no referencia de vuelta al Pedido.

La aplicación permite crear, leer, actualizar y eliminar (CRUD) ambas entidades, manteniendo la integridad mediante transacciones JDBC.

---

## Arquitectura del proyecto
El código está organizado en capas dentro del paquete `proyectotfi`:

```
proyectotfi.config     → Conexión MySQL + ejecución de scripts SQL  
proyectotfi.entities   → Entidades del dominio (Pedido, Envio)  
proyectotfi.entities.enums → Estados y tipos asociados  
proyectotfi.dao        → Acceso a datos (DAOs + JDBC)  
proyectotfi.service    → Lógica de negocio + validaciones + transacciones  
proyectotfi.main       → Menú interactivo de consola
```

---

## Requisitos técnicos
### Software necesario
- **Java 21**
- **MySQL 8.0**
- **Conector JDBC de MySQL**
- Librería **Dotenv** para cargar variables desde `.env`

### Archivo `.env` esperado
Debe ubicarse en `/src/proyectotfi/config/`:

```
DB_HOST=[host]
DB_PORT=[port]
DB_NAME=proyectotfi
DB_USER=root
DB_PASS=[pass]
```

---

## Base de datos – Scripts SQL

Los archivos se encuentran en:

```
/src/proyectotfi/config/
```

| Archivo | Función |
|--------|---------|
| `01_esquema.sql` | Crea la base y las tablas |
| `03_carga_masiva.sql` | Inserta datos de prueba |
| `RunScriptsSQL.java` | Permite ejecutarlos automáticamente desde Java |

### ▶ Crear la base manualmente:

```bash
mysql -u root -p < 01_esquema.sql
mysql -u root -p < 03_carga_masiva.sql
```

---

##  Cómo compilar y ejecutar el proyecto

### 1️⃣ Compilar (si se hace por consola)
```bash
javac -cp .:mysql-connector-j.jar proyectotfi/main/Main.java
```

### 2️⃣ Ejecutar
```bash
java -cp .:mysql-connector-j.jar proyectotfi.main.Main
```

### Credenciales de prueba
Usuario MySQL: **root**  
Contraseña: **[pass]** (configurada en `.env`)

---

## Flujo de uso (menú)
Desde la consola podrás:

### Para Envíos
- Crear Envío  
- Listar Envíos  
- Buscar por ID  
- Actualizar datos  
- Eliminar (soft-delete)

### Para Pedidos
- Crear Pedido con Envío obligatorio  
- Listar Pedidos  
- Buscar por ID  
- Actualizar campos  
- Eliminar (soft-delete)

### Validaciones implementadas
- Tracking único para cada Envío  
- Totales y costos positivos  
- Fechas válidas  
- No se permite un Pedido sin Envío  
- No se permite asociar más de un Envío al mismo Pedido  
- Rollback automático ante errores  

---

## Manejo de transacciones
La capa `Service` controla:

```java
conn.setAutoCommit(false);
...
conn.commit();
conn.rollback();
```

Esto garantiza que *Pedido + Envio* se creen/actualicen juntos dentro de la misma transacción.

---

## UML
El diagrama UML se encuentra en:

```
/src/proyectotfi/docs/Diagrama_UML.png
```

Representa la relación:

```
Pedido → Envio (1:1 unidireccional)
```

---

## 📄 Informe
El informe completo en PDF está en:

```
/src/proyectotfi/docs/Informe_TFI.pdf
```

---

## Video del proyecto
**[ https://drive.google.com/file/d/1LbIPYBHysrLO3ux1eTZD7J7LxQqorlVy/view?usp=drive_link ]**  


---

## Herramientas utilizadas
- Java 21
- MySQL 8  
- JDBC  
- Dotenv  
- Draw.io / StarUML para UML  
- Git & GitHub  
- NetBeans  
- ChatGPT para apoyo

===

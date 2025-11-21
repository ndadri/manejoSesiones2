package models;

import java.time.LocalDate;

/**
 * Clase Producto que representa un objeto con sus atributos básicos:
 * id del producto, nombre, tipo y precio.
 *
 * Descripción:
 * Esta clase se utiliza para modelar los datos de un producto dentro del sistema,
 * permitiendo almacenar y acceder a su información de manera encapsulada mediante
 * métodos getters y setters.
 */

public class Producto {
    private Long id;
    private String nombreProducto;
    private Categoria categoria;
    private int stock;
    private String descripcion;
    private double precio;
    private String codigo;
    private LocalDate fechaElaboracion;
    private LocalDate fechaCaducidad;
    private int condicion;

    public Producto() {
    }

    public Producto(Long id, String nombreProducto, int cantidad, String descripcion, double precio, String codigo, String tipo, LocalDate fechaElaboracion, LocalDate fechaCaducidad, int condicion) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        Categoria categoria = new Categoria();
        categoria.setNombre(tipo);
        this.stock = stock;
        this.descripcion = descripcion;
        this.precio = precio;
        this.codigo = codigo;
        this.fechaElaboracion = fechaElaboracion;
        this.fechaCaducidad = fechaCaducidad;
        this.condicion = condicion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaElaboracion() {
        return fechaElaboracion;
    }

    public void setFechaElaboracion(LocalDate fechaElaboracion) {
        this.fechaElaboracion = fechaElaboracion;
    }

    public LocalDate getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(LocalDate fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }
}

package models;

import java.util.Objects;

public class ItemCarro {
    private int cantidad;
    private Producto producto;

    public ItemCarro(int cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
    }

    public int getCantidad() { return cantidad; }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad; }

    public Producto getProducto() {
        return producto; }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    /**
     * Creamos un metodo para comparar si ya un producto esta en la lista del carrito
     * de compras y no repetirlo
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemCarro itemCarro = (ItemCarro) o;
        return Objects.equals(producto.getIdProducto(), itemCarro.producto.getIdProducto())
                && Objects.equals(cantidad, itemCarro.cantidad);
    }

    public double getSubtotal() {
        return cantidad * producto.getPrecio();
    }
}


package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DetalleCarro {
    private List<ItemCarro> items;

    public DetalleCarro() {this.items = new ArrayList<>();}

    /**Implementamos un metodo para agregar un producto al carro
     * si el producto ya está en el carrito no lo duplica si no que aumenta la cantidad
     * si no está, lo agrega como nuevo*/
    public void addItemCarro(ItemCarro itemCarro) {
        if(items.contains(itemCarro)) {
            Optional<ItemCarro> optionalItemCarro = items.stream()
                    .filter(i -> i.equals(itemCarro))
                    .findAny();
            if(optionalItemCarro.isPresent()) {
                ItemCarro i = optionalItemCarro.get();
                i.setCantidad(i.getCantidad() + 1);
            }
        }else{
            this.items.add(itemCarro);
        }
    }

    /**
     * Metodo que retorna la lista de productos del carrito
     */
    public List<ItemCarro> getItem() {return items;}


    /**
     * metodo que calcula el subtotal sin iva
     */
    public double getSubtotal() {
        return items.stream().mapToDouble(ItemCarro::getSubtotal).sum();
    }

    /**
     * metodo que calcula el iva del subtotal
     */
    public double getTotalIva() {
        return getSubtotal() * 0.15;
    }

    /**
     * metodo que calcula el total con iva ya incluido
     */
    public double getTotal() {
        return getSubtotal() + getTotalIva();
    }

}

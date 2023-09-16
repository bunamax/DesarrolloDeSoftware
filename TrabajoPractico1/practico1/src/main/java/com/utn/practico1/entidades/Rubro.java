package com.utn.practico1.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rubro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String denominacion;

    // RELACION ONE TO ONE UNIDIRECCIONAL
    // El Cascadeo propaga las operaciones y orphanRemoval asegura que se elimine la relacionada
// OJO COMO LA CARGA ES POR DEFECTO LAZY SI NO PONGO EAGER ME DA ERROR PORQUE CIERRA LA SESIÓN
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)

    @JoinColumn(name = "rubro_id")

    // OJO ES IMPORTANTE COLOCAR ESTA ANOTACIÓN SINO ME DA ERROR

    @Builder.Default
    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto prod) {

        productos.add(prod);
    }


    public void mostrarProductos() {
        System.out.println("Pedidos de " + denominacion + ":");
        for (Producto producto : productos) {
            System.out.println("Tipo: " + producto.getTipoProducto() + ", Tiempo Estimado: " + producto.getTiempoEstimadoCocina()+"Denominacion: " +
                    producto.getDenomicacion()+"Precio Venta: " + producto.getPrecioVenta()+"Precio Compra: " + producto.getPrecioCompra()+
                    "Stock Actual: " + producto.getStockActual()+"Stock Min: " + producto.getStockMinimo()+"Unidad: " + producto.getUnidadMedida()+
                    "Receta: " + producto.getReceta() );
        }


    }

}
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
public class Cliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    private String apellido;
    private int telefono;
    private String email;

    // RELACION ONE TO ONE UNIDIRECCIONAL
    // El Cascadeo propaga las operaciones y orphanRemoval asegura que se elimine la relacionada
// OJO COMO LA CARGA ES POR DEFECTO LAZY SI NO PONGO EAGER ME DA ERROR PORQUE CIERRA LA SESIÓN

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)

    @JoinColumn(name = "persona_id")

    // OJO ES IMPORTANTE COLOCAR ESTA ANOTACIÓN SINO ME DA ERROR

    @Builder.Default
    private List<Domicilio> domicilios = new ArrayList<>();

    public void agregarDomicilio(Domicilio domi) {

        domicilios.add(domi);
    }


    public void mostrarDomicilios() {
        System.out.println("Domicilios de " + nombre + " " + apellido + ":");
        for (Domicilio domicilio : domicilios) {
            System.out.println("Calle: " + domicilio.getCalle() + ", Número: " + domicilio.getNumero()+ ", Localidad: " + domicilio.getLocalidad());
        }


    }
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)

    @JoinColumn(name = "persona_id")

    @Builder.Default
    private List<Pedido> pedidos = new ArrayList<>();

    public void agregarPedido(Pedido pedi) {

        pedidos.add(pedi);
    }


    public void mostrarPedidos() {
        System.out.println("Pedidos de " + nombre + " " + apellido + ":");
        for (Pedido pedido : pedidos) {
            System.out.println("estado: " + pedido.getEstado() + ", fecha: " + pedido.getFechaPedido()+ ", tipoEnvio: " + pedido.getTipoEnvio()+ ", total: " + pedido.getTotal());
        }


    }

}
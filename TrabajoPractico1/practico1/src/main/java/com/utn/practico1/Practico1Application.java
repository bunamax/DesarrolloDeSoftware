package com.utn.practico1;


import com.utn.practico1.entidades.*;
import com.utn.practico1.repositorios.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootApplication
public class Practico1Application {
	@Autowired
	RubroRepository rubroRepository;
	@Autowired
	ProductoRepository productoRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	DomicilioRepository domicilioRepository;
	@Autowired
	PedidoRepository pedidoRepository;
	@Autowired
	DetallePedidoRepository detallePedidoRepository;
	@Autowired
	FacturaRepository facturaRepository;
	public static void main(String[] args) {
		SpringApplication.run(Practico1Application.class, args);



	}



	@Bean
	CommandLineRunner init(RubroRepository rubroRepo,ProductoRepository productoRepo, ClienteRepository clienteRepo,DomicilioRepository domicilioRepo,
						   PedidoRepository pedidoRepo,DetallePedidoRepository detallerepo,FacturaRepository facturaRepo) {
		return args -> {
			System.out.println("-----------------ESTOY FUNCIONANDO---------");



/*El método builder() se genera automáticamente por Lombok
y te permite crear una instancia de Persona.Builder.
Luego, puedes encadenar llamadas a los métodos
setters generados automáticamente para establecer los
valores de los atributos de la clase.
Finalmente, build() crea la instancia
 de la clase Persona con los valores proporcionados.

 */
			SimpleDateFormat formatoFecha = new SimpleDateFormat( "yyyy-MM-dd");

			BigDecimal miBigDecimal = new BigDecimal("123.45");
			String fechaString = "2023-09-09";

			Date fecha = formatoFecha.parse(fechaString);

			Rubro rubro = Rubro.builder()

					.denominacion("cocina")
					.build();

			Producto producto1 = Producto.builder()

					.tipoProducto("manufacturado")
					.tiempoEstimadoCocina(30)
					.denomicacion("pasta")
					.precioVenta(230)
					.precioCompra(200)
					.stockActual(100)
					.stockMinimo(20)
					.unidadMedida("kg")
					.receta("meter al agua hasta que se cocinen")
					.build();
			Producto producto2 = Producto.builder()

					.tipoProducto("insumo")
					.tiempoEstimadoCocina(30)
					.denomicacion("camaron")
					.precioVenta(330)
					.precioCompra(300)
					.stockActual(200)
					.stockMinimo(40)
					.unidadMedida("kg")
					.receta("meter al agua hasta que se cocinen")
					.build();


			rubro.agregarProducto(producto1);
			rubro.agregarProducto(producto2);

			rubroRepository.save(rubro);
// Crear instancia de Cliente y agregar domicilios
			Cliente cliente = Cliente.builder()
					.nombre("Juan")
					.apellido("Pérez")
					.telefono(4324324)
					.email("hola@gmail.com")
					.build();
			
			Domicilio domicilio1 = Domicilio.builder()
					.calle("Calle 1")
					.numero(123)
					.localidad("godoycruz")
					.build();

			Domicilio domicilio2 = Domicilio.builder()
					.calle("Calle 2")
					.numero(456)
					.localidad("guaymallen")
					.build();

			Pedido pedido1 = Pedido.builder()
					.estado("iniciado")
					.fechaPedido(fecha)
					.tipoEnvio("delivery")
					.total(23.99)
					.build();
			Pedido pedido2 = Pedido.builder()
					.estado("preparacion")
					.fechaPedido(fecha)
					.tipoEnvio("retira")
					.total(11.99)
					.build();
			DetallePedido detallePedido1 = DetallePedido.builder()
					.cantidad(5)
					.subtotal(250)
					.producto(producto1)
					.build();

			DetallePedido detallePedido2 = DetallePedido.builder()
					.cantidad(4)
					.subtotal(150)
					.producto(producto2)
					.build();



			cliente.agregarDomicilio(domicilio1);
			cliente.agregarDomicilio(domicilio2);
			cliente.agregarPedido(pedido1);
			cliente.agregarPedido(pedido2);

			Factura factura1 = Factura.builder()
					.numero(123)
					.fechaFactura(fecha)
					.descuento(123)
					.formaPago("efectivo")
					.total(22)
					.build();
			Factura factura2 = Factura.builder()
					.numero(12)
					.fechaFactura(fecha)
					.descuento(10)
					.formaPago("efectivo")
					.total(30)
					.build();



			// Guardar el objeto Cliente en la base de datos

			clienteRepository.save(cliente);


			pedido1.setFactura(factura1);
			pedido2.setFactura(factura2);
			pedido1.agregarDetallePedido(detallePedido1);
			pedido2.agregarDetallePedido(detallePedido2);
			pedidoRepository.save(pedido1);
			pedidoRepository.save(pedido2);




			// Recuperar el objeto Persona desde la base de datos

			Rubro rubroRecuperado = rubroRepository.findById(rubro.getId()).orElse(null);


			if (rubroRecuperado != null){
				System.out.println("------------------------------------------------------");
				System.out.println("Se encontró un rubro con los siguientes datos:");
				System.out.println("------------------------------------------------------");
				System.out.println("Denominación: " + rubroRecuperado.getDenominacion());
				rubro.mostrarProductos();
			}


			Cliente clienteRecuperado = clienteRepository.findById(cliente.getId()).orElse(null);


			if (clienteRecuperado != null) {
				System.out.println("Nombre: " + clienteRecuperado.getNombre());
				System.out.println("Apellido: " + clienteRecuperado.getApellido());
				System.out.println("Telefono: " + clienteRecuperado.getTelefono());
				System.out.println("Mail: " + clienteRecuperado.getEmail());
				clienteRecuperado.mostrarDomicilios();
				clienteRecuperado.mostrarPedidos();


			}
			for (Pedido pedidoRecuperado1 : clienteRecuperado.getPedidos()){
				if (pedidoRecuperado1.getFactura() != null){
					System.out.println("------------------------------------------------------");
					System.out.println("Se encontro una factura con los siguientes detalles: ");
					System.out.println("------------------------------------------------------");
					System.out.println("Número: " + pedidoRecuperado1.getFactura().getNumero());
					System.out.println("Fecha: " + pedidoRecuperado1.getFactura().getFechaFactura());
					System.out.println("Descuento: " + pedidoRecuperado1.getFactura().getDescuento());
					System.out.println("Forma de pago: " + pedidoRecuperado1.getFactura().getFormaPago());
					System.out.println("Total: " + pedidoRecuperado1.getFactura().getTotal());
					pedidoRecuperado1.mostrarDetallePedidos();
				}
				for (DetallePedido detallePedido : pedidoRecuperado1.getDetallePedidos()){
					System.out.println("------------------------------------------------------");
					System.out.println("Se encontró el siguiente detalle de pedido:");
					System.out.println("------------------------------------------------------");
					System.out.println("Cantidad: " + detallePedido1.getCantidad());
					System.out.println("Subtotal: " + detallePedido1.getSubtotal());
					System.out.println("El producto ordenado es el siguiente: ");
					System.out.println("Tipo: " + detallePedido1.getProducto().getTipoProducto());
					System.out.println("Tiempo estimado de cocina: " + detallePedido1.getProducto().getTiempoEstimadoCocina());
					System.out.println("Denominación: " + detallePedido1.getProducto().getDenomicacion());
					System.out.println("Precio de venta: " + detallePedido1.getProducto().getPrecioVenta());
					System.out.println("Precio de compra: " + detallePedido1.getProducto().getPrecioCompra());
					System.out.println("Stock actual: " + detallePedido1.getProducto().getStockActual());
					System.out.println("Stock mínimo: " + detallePedido1.getProducto().getStockMinimo());
					System.out.println("Unidad de medida: " + detallePedido1.getProducto().getUnidadMedida());
					System.out.println("Receta: " + detallePedido1.getProducto().getReceta());
				}
			}



		};

	}

}


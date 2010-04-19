package kdw.aliverti.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import kdw.aliverti.model.Product;


public class ProductTest {
	
	@Test
	public void product_mochila_getUseVenta() {
		// arrange
		Product product = new Product();
		product.setName("mochila");
			
		// act
		
		// assert
		assertEquals("Venta", product.getUse());
	}
	
	@Test
	public void product_alquilerMochila_getUseAlquiler() {
		// arrange
		Product product = new Product();
		product.setName("alquiler mochila");
			
		// act
		
		// assert
		assertEquals("Alquiler", product.getUse());
	}
	
	@Test
	public void product_mochilaAlquiler_getUseAlquiler() {
		// arrange
		Product product = new Product();
		product.setName("mochila alquiler");
			
		// act
		
		// assert
		assertEquals("Alquiler", product.getUse());
	}
	
	@Test
	public void product_mochilaAzul_getSaleTypeAzul() {
		// arrange
		Product product = new Product();
		product.setName("mochila azul");
			
		// act
		
		// assert
		assertEquals("Azul", product.getSaleType());
	}
	
	@Test
	public void product_mochilaRojo_getSaleTypeRojo() {
		// arrange
		Product product = new Product();
		product.setName("mochila rojo");
			
		// act
		
		// assert
		assertEquals("Rojo", product.getSaleType());
	}
	
	@Test
	public void product_mochila_getSaleTypeAzul() {
		// arrange
		Product product = new Product();
		product.setName("mochila");
			
		// act
		
		// assert
		assertEquals("Azul", product.getSaleType());
	}
}

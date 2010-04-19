package kdw.aliverti.test;

import kdw.aliverti.model.Brand;
import kdw.aliverti.model.Product;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"/launch-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductProcessorTest {
	@Autowired
	private ItemProcessor<Product, Product> productProcessor;
	
	@Test
	public void processor_product_returnSame() throws Exception {
		//Arrange
		Product productOut;
		Product productIn = getProduct();
		
		// Act
		productOut = productProcessor.process(productIn);
		
		// Assert
		Assert.assertEquals("a", productOut.getId());
	}
	
	@Test
	public void processor_brand1word_returnCamel() throws Exception {
		//Arrange
		Product productOut;
		Product productIn = getProduct();
		
		// Act
		productOut = productProcessor.process(productIn);
		
		// Assert
		Assert.assertEquals("Aliverti", productOut.getBrand().getName());
	}
	
	@Test
	public void processor_brand2word_returnCamel() throws Exception {
		//Arrange
		Product productOut;
		Product productIn = getProduct();
		productIn.getBrand().setName("aliverti marca");
		
		// Act
		productOut = productProcessor.process(productIn);
		
		// Assert
		Assert.assertEquals("Aliverti Marca", productOut.getBrand().getName());
	}

	private Product getProduct() {
		Product productIn = new Product();
		
		productIn.setId("a");
		Brand brandIn = new Brand();
		brandIn.setName("aliverti");
		productIn.setBrand(brandIn);
		
		return productIn;
	}
	
}

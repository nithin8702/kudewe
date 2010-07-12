package kdw.zentidos.test;

import kdw.zentidos.model.Category;
import kdw.zentidos.model.Product;

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
		Product productIn = new Product();
		productIn.setName("A");
		
		// Act
		productOut = productProcessor.process(productIn);
		
		// Assert
		Assert.assertEquals("A", productOut.getName());
	}
	
	@Test
	public void processor_productPilates_returnCategoryPilates() throws Exception {
		//Arrange
		Product productOut;
		Product productIn = new Product();
		productIn.setName("Mensual Pilates 8");
		
		// Act
		productOut = productProcessor.process(productIn);
		Category category = productOut.getCategory();
		
		// Assert
		Assert.assertEquals(1, category.getId());
		Assert.assertEquals("Pilates", category.getName());
	}
	
	@Test
	public void processor_productPlat_returnCategoryPlataforma() throws Exception {
		//Arrange
		Product productOut;
		Product productIn = new Product();
		productIn.setName("Mensual Plat x 4");
		
		// Act
		productOut = productProcessor.process(productIn);
		Category category = productOut.getCategory();
		
		// Assert
		Assert.assertEquals(2, category.getId());
		Assert.assertEquals("Plataforma", category.getName());
	}
	
	@Test
	public void processor_productCombo_returnCategoryCombo() throws Exception {
		//Arrange
		Product productOut;
		Product productIn = new Product();
		productIn.setName("Combo 1");
		
		// Act
		productOut = productProcessor.process(productIn);
		Category category = productOut.getCategory();
		
		// Assert
		Assert.assertEquals(4, category.getId());
		Assert.assertEquals("Combo", category.getName());
	}
	
	@Test
	public void processor_productOther_returnCategoryGabinete() throws Exception {
		//Arrange
		Product productOut;
		Product productIn = new Product();
		productIn.setName("Esfero");
		
		// Act
		productOut = productProcessor.process(productIn);
		Category category = productOut.getCategory();
		
		// Assert
		Assert.assertEquals(3, category.getId());
		Assert.assertEquals("Gabinete", category.getName());
	}
}


package kdw.zentidos.test;

import javax.annotation.Resource;

import kdw.zentidos.model.Product;
import kdw.zentidos.model.Sale;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"/launch-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SaleProcessorTest {
	@Resource
	private ItemProcessor<Sale, Sale> saleProcessor;
	
	@Test
	public void processor_sale_returnSame() throws Exception {
		//Arrange
		Sale saleOut;
		
		Sale saleIn = new Sale();
		saleIn.setProduct(new Product());
		saleIn.setQuantity(1);
		
		// Act
		saleOut = saleProcessor.process(saleIn);
		
		// Assert
		Assert.assertEquals(1, saleOut.getQuantity());
	}
	
	@Test
	public void processor_saleCombo1_returnId27() throws Exception {
		//Arrange
		Sale saleOut;
		
		Product product = new Product();
		product.setName("Combo 1");
		
		Sale saleIn = new Sale();
		saleIn.setProduct(product);
		
		// Act
		saleOut = saleProcessor.process(saleIn);
		
		// Assert
		Assert.assertEquals(27, saleOut.getProduct().getId());
	}
}


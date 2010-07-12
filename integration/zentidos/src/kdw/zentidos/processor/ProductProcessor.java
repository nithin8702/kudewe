package kdw.zentidos.processor;

import kdw.zentidos.model.Category;
import kdw.zentidos.model.Product;

import org.springframework.batch.item.ItemProcessor;

public class ProductProcessor implements ItemProcessor<Product, Product>{

	public Product process(Product input) throws Exception {
		Product output = input;
		output.setCategory(getCategory(input.getName()));
		return output;
	}
	
	private Category getCategory(String productName) {
		Category category = new Category();
		
		if (productName.toLowerCase().contains("pilates")) {
			category.setId(1);
			category.setName("Pilates");
		} else if (productName.toLowerCase().contains("plat")) {
			category.setId(2);
			category.setName("Plataforma");
		} else if (productName.toLowerCase().contains("combo")) {
			category.setId(4);
			category.setName("Combo");
		} else {
			category.setId(3);
			category.setName("Gabinete");
		}
		return category;
	}
	
}

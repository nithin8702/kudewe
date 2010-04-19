package kdw.aliverti.processor;

import kdw.aliverti.model.Product;

import org.springframework.batch.item.ItemProcessor;

public class ProductProcessor implements ItemProcessor<Product, Product>{

	public Product process(Product input) throws Exception {
		Product output = input;
		
		output.getBrand().setName(convertToCamelCase(input.getBrand().getName()));
		
		// TODO Auto-generated method stub
		return output;
	}
	
	private String convertToCamelCase(String cn) {
		StringBuffer sb = new StringBuffer();
		String[] str = cn.split(" ");
		boolean firstTime = true;
		for(String temp : str) {
			if(!firstTime) {
				sb.append(" ");
			} else {
				firstTime = false;
			}
			sb.append(Character.toUpperCase(temp.charAt(0)));
			sb.append(temp.substring(1).toLowerCase());
		}
        return sb.toString(); 		
	}
	
}

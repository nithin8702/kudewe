package kdw.zentidos.processor;

import kdw.zentidos.model.Sale;
import kdw.zentidos.repository.LkProductRepository;

import org.springframework.batch.item.ItemProcessor;

public class SaleProcessor implements ItemProcessor<Sale, Sale>{
	private LkProductRepository lkProductRepository;
	
	public void setLkProductRepository(LkProductRepository lkProductRepository) {
		this.lkProductRepository = lkProductRepository;
	}
	
	public Sale process(Sale input) throws Exception {
		Sale output = input;
		output.setProduct(lkProductRepository.getByDescription(input.getProduct().getName()));
		return output;
	}
	
}

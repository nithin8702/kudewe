package kdw.zentidos.model;

import kdw.core.model.LkSimple;

public class Product extends LkSimple {
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}

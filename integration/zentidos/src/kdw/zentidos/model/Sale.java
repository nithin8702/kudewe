package kdw.zentidos.model;

import java.math.BigDecimal;

import kdw.core.model.DateValue;
import kdw.core.model.LkSimple;
import kdw.zentidos.model.Product;

public class Sale {
	private DateValue date;
	private LkSimple ageRange;
	private Product product;
	private int quantity;
	private BigDecimal price;
	
	public DateValue getDate() {
		return date;
	}
	public void setDate(DateValue date) {
		this.date = date;
	}
	public LkSimple getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(LkSimple ageRange) {
		this.ageRange = ageRange;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}

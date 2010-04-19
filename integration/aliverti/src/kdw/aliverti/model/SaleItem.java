package kdw.aliverti.model;

import java.math.BigDecimal;

public class SaleItem {
	private BigDecimal price;
	private BigDecimal cost;
	private int quantity;
	private Product product;
	private DateValue date;
	
	public BigDecimal getPrice() {
		return price;
	}
	public SaleItem setPrice(BigDecimal price) {
		this.price = price;
		return this;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public SaleItem setCost(BigDecimal cost) {
		this.cost = cost;
		return this;
	}
	public int getQuantity() {
		return quantity;
	}
	public SaleItem setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}
	public Product getProduct() {
		return product;
	}
	public SaleItem setProduct(Product product) {
		this.product = product;
		return this;
	}
	public DateValue getDate() {
		return date;
	}
	public SaleItem setDate(DateValue date) {
		this.date = date;
		return this;
	}
}

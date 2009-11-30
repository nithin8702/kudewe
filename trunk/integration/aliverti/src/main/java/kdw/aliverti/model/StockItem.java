package kdw.aliverti.model;

import java.math.BigDecimal;

public class StockItem {
	private Product product;
	private DateValue date;
	private int quantity;
	private BigDecimal cost;
	public Product getProduct() {
		return product;
	}
	public StockItem setProduct(Product product) {
		this.product = product;
		return this;
	}
	public DateValue getDate() {
		return date;
	}
	public StockItem setDate(DateValue date) {
		this.date = date;
		return this;
	}
	public int getQuantity() {
		return quantity;
	}
	public StockItem setQuantity(int quantity) {
		this.quantity = quantity;
		return this;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public StockItem setCost(BigDecimal cost) {
		this.cost = cost;
		return this;
	}
	
}

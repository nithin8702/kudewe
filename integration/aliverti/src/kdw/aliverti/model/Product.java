package kdw.aliverti.model;

public class Product {
	private Brand brand;
	private String id;
	private String name;
	private Category Category1;
	private Category Category2;
	private Category Category3;
	
	public Category getCategory1() {
		return Category1;
	}

	public Product setCategory1(Category category1) {
		Category1 = category1;
		return this;
	}

	public Category getCategory2() {
		return Category2;
	}

	public Product setCategory2(Category category2) {
		Category2 = category2;
		return this;
	}

	public Category getCategory3() {
		return Category3;
	}

	public Product setCategory3(Category category3) {
		Category3 = category3;
		return this;
	}

	public String getId() {
		return id;
	}

	public Product setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Product setName(String name) {
		this.name = name;
		return this;
	}

	public Brand getBrand() {
		return brand;
	}

	public Product setBrand(Brand brand) {
		this.brand = brand;
		return this;
	}

	public String getUse() {
		String use = "Venta";
		if (name.toLowerCase().contains("alquiler")) {
			use = "Alquiler";
		}
		return use;
	}

	public String getSaleType() {
		String[] words = name.split(" ");
		String saleType = words[words.length - 1].toLowerCase();
		if("rojo".equals(saleType)) {
			saleType = "Rojo";
		} else {
			saleType = "Azul";
		}
		
		return saleType;
	}
}

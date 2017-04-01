package gui.manager.OrLearning;

public class Product {

	private String name;
	private double price;
	private int quantity;

	public Product() {
		name = "";
		price = quantity = 0;
	}

	public Product(final String name, final double price, final int quantity) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(final int quantity) {
		this.quantity = quantity;
	}

}
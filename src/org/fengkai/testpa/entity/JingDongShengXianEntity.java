package org.fengkai.testpa.entity;

public class JingDongShengXianEntity {

	private String name;
	private String price;
	private String weight;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "JingDongShengXianEntity [name=" + name + ", price=" + price + ", weight=" + weight + "]";
	}
	
	

}

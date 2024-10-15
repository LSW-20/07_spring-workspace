package com.br.spring.di;

public class Phone {

	private String name;
	private String brand;
	private int price;
	private String releaseDate;
	
	public Phone() {}

	public Phone(String name, String brand, int price, String releaseDate) {
		super();
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.releaseDate = releaseDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Phone [name=" + name + ", brand=" + brand + ", price=" + price + ", releaseDate=" + releaseDate + "]";
	}
	
	
	
}

package com.example.myapp.bean;

public class Order {
	private String shopName;
	private String address;
	private String foodName;
	private String price;
	private int count;
	private int img;
	
	
	
	public int getImg() {
		return img;
	}
	public void setImg(int img) {
		this.img = img;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Order() {
		
	}

	public Order(String shopName, String address, String foodName, String price, int count, int img) {
		super();
		this.shopName = shopName;
		this.address = address;
		this.foodName = foodName;
		this.price = price;
		this.count = count;
		this.img = img;
	}
	@Override
	public String toString() {
		return "Order [shopName=" + shopName + ", address=" + address + ", foodName=" + foodName + ", price=" + price
				+ ", count=" + count + "]";
	}
	
	
	
	
	

}

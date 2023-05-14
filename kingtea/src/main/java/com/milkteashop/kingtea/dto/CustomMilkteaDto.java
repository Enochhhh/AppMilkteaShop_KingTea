package com.milkteashop.kingtea.dto;

import java.util.List;


public class CustomMilkteaDto {
	
	private String customMilkteaId;
	
	private String size;
	
	private String sugarAmount;
	
	private String iceAmount;
	
	private String milkTeaId;
	
	private boolean enabled;
	
	List<String> listTopping;
	
	int quantity;
	
	int totalPriceOfItem;
	
	String nameMilktea;

	public String getCustomMilkteaId() {
		return customMilkteaId;
	}

	public void setCustomMilkteaId(String customMilkteaId) {
		this.customMilkteaId = customMilkteaId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getSugarAmount() {
		return sugarAmount;
	}

	public void setSugarAmount(String sugarAmount) {
		this.sugarAmount = sugarAmount;
	}

	public String getIceAmount() {
		return iceAmount;
	}

	public void setIceAmount(String iceAmount) {
		this.iceAmount = iceAmount;
	}

	public String getMilkTeaId() {
		return milkTeaId;
	}

	public void setMilkTeaId(String milkTeaId) {
		this.milkTeaId = milkTeaId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public List<String> getListTopping() {
		return listTopping;
	}

	public void setListTopping(List<String> listTopping) {
		this.listTopping = listTopping;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getTotalPriceOfItem() {
		return totalPriceOfItem;
	}

	public void setTotalPriceOfItem(int totalPriceOfItem) {
		this.totalPriceOfItem = totalPriceOfItem;
	}

	public String getNameMilktea() {
		return nameMilktea;
	}

	public void setNameMilktea(String nameMilktea) {
		this.nameMilktea = nameMilktea;
	}
}

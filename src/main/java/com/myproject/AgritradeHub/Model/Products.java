package com.myproject.AgritradeHub.Model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Products {
      
	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private long id;
	  
	  @Column(nullable = false)
	  private String productName;
	  
	  private String ProductImage;
	  
	  @Column(nullable = false)
	  private String category;
	  
	  
	  @Column(nullable = false)
	  private int quantity;
	  
	  @Column(nullable = false)
	  private BigDecimal pricePerUnit;
	 
	  @ManyToOne
	  private AllUsers farmer;
	  
	  @Enumerated(EnumType.STRING)
	  private ProductStatus status;
	  
	  public enum ProductStatus{
		  AVAILABLE, OUT_OF_STOCK
	  }

	  public long getId() {
		  return id;
	  }

	  public void setId(long id) {
		  this.id = id;
	  }

	  public String getProductName() {
		  return productName;
	  }

	  public void setProductName(String productName) {
		  this.productName = productName;
	  }

	  public String getProductImage() {
		  return ProductImage;
	  }

	  public void setProductImage(String productImage) {
		  ProductImage = productImage;
	  }

	  public String getCategory() {
		  return category;
	  }

	  public void setCategory(String category) {
		  this.category = category;
	  }

	  public int getQuantity() {
		  return quantity;
	  }

	  public void setQuantity(int quantity) {
		  this.quantity = quantity;
	  }

	  public BigDecimal getPricePerUnit() {
		  return pricePerUnit;
	  }

	  public void setPricePerUnit(BigDecimal pricePerUnit) {
		  this.pricePerUnit = pricePerUnit;
	  }

	  public AllUsers getFarmer() {
		  return farmer;
	  }

	  public void setFarmer(AllUsers farmer) {
		  this.farmer = farmer;
	  }

	  public ProductStatus getStatus() {
		  return status;
	  }

	  public void setStatus(ProductStatus status) {
		  this.status = status;
	  }
	  
	  
}

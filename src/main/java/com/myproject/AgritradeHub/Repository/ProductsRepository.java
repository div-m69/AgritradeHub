package com.myproject.AgritradeHub.Repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.myproject.AgritradeHub.Model.AllUsers;
import com.myproject.AgritradeHub.Model.Products;

public interface ProductsRepository extends JpaRepository<Products, Long>{

	List<Products> findAllByFarmer(AllUsers farmer);

	List<Products> findAllByCategory(String categoryName);

	Object countByFarmer(AllUsers farmer);
	
	@Query("SELECT SUM(p.pricePerUnit * p.quantity) FROM Products p WHERE p.farmer.id = :farmerId AND p.status = 'AVAILABLE'")
	BigDecimal calculateInStockRevenue(@Param("farmerId") Long farmerId);

}

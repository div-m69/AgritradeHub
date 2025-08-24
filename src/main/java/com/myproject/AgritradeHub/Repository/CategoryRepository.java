package com.myproject.AgritradeHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.AgritradeHub.Model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}

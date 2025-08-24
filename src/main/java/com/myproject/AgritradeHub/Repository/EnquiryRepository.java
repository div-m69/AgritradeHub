package com.myproject.AgritradeHub.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.AgritradeHub.Model.Enquiry;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {

	//List<Enquiry> findTop5ByOrderByEnquiryDateDesc();

}
 
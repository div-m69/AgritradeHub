package com.myproject.AgritradeHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.AgritradeHub.Model.Orders;
import com.myproject.AgritradeHub.Model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	Payment findByOrder(Orders order);

}

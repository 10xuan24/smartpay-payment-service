package com.smartpay.paymentservice.repository;
import java.util.List;
import com.smartpay.paymentservice.entity.User;

import com.smartpay.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByUser(User user);

}

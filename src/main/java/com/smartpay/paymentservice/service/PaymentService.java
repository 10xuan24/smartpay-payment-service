package com.smartpay.paymentservice.service;

import com.smartpay.paymentservice.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment createPayment(Payment payment);
    Payment getPaymentById(Long id);
    List<Payment> getAllPayments();
    Payment updatePayment(Long id, Payment payment);
    void deletePayment(Long id);
}

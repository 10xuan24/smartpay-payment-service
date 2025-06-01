package com.smartpay.paymentservice.service.impl;

import com.smartpay.paymentservice.entity.Payment;
import com.smartpay.paymentservice.repository.PaymentRepository;
import com.smartpay.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.smartpay.paymentservice.entity.User;
import com.smartpay.paymentservice.repository.UserRepository;
import java.time.LocalDateTime;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Payment payment) {
        // 获取当前登录用户
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 返回的是 Optional<User>
        Optional<User> optionalUser = userRepository.findByUsername(username);
        optionalUser.ifPresent(payment::setUser); // 关键！这步必须有
        if (optionalUser.isPresent()) {
            payment.setUser(optionalUser.get());
        }

        payment.setTimestamp(LocalDateTime.now());
        System.out.println("当前登录用户：" + username);
        System.out.println("数据库中是否存在该用户：" + optionalUser.isPresent());
        return paymentRepository.save(payment);
    }



    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Payment> getAllPayments() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional.orElse(null);
        if (user == null) {
            return new ArrayList<>(); // 或者抛出异常，看你项目需求
        }
        return paymentRepository.findByUser(user);
    }


    @Override
    public Payment updatePayment(Long id, Payment payment) {
        Optional<Payment> optional = paymentRepository.findById(id);
        if (optional.isPresent()) {
            Payment existing = optional.get();
            existing.setAmount(payment.getAmount());
            existing.setDescription(payment.getDescription());
            // 其他字段可以继续添加
            return paymentRepository.save(existing);
        }
        return null;
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    @Autowired
    private UserRepository userRepository;




}

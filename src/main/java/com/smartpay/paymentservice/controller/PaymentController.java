package com.smartpay.paymentservice.controller;

import com.smartpay.paymentservice.entity.Payment;
import com.smartpay.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import com.smartpay.paymentservice.dto.PaymentRequestDTO;
import com.smartpay.paymentservice.dto.PaymentResponseDTO;



@Tag(name = "支付接口", description = "处理支付相关的操作")


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // 1. 创建支付记录
    @Operation(summary = "创建支付", description = "根据提交的信息创建一条新的支付记录")
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody PaymentRequestDTO dto) {
        // 构建实体对象
        Payment payment = new Payment();
        payment.setAmount(dto.getAmount()); // 设置金额
        payment.setDescription(dto.getDescription()); // 设置描述
        payment.setStatus("CREATED"); // 设置初始状态为 CREATED
        payment.setChannel(dto.getChannel()); // ✅ 设置支付渠道枚举值（重点）

        // 保存到数据库
        Payment saved = paymentService.createPayment(payment);

        // 构建返回 DTO
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setId(saved.getId());
        response.setAmount(saved.getAmount());
        response.setDescription(saved.getDescription());
        response.setStatus(saved.getStatus());
        response.setChannel(saved.getChannel()); // ✅ 返回支付渠道枚举值

        return ResponseEntity.ok(response);
    }


    // 2. 获取所有支付记录
    @Operation(summary = "根据 ID 获取支付记录", description = "根据支付记录的 ID 查询详细信息")
    @GetMapping
    public List<PaymentResponseDTO> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();

        // 转换成 DTO
        return payments.stream().map(payment -> {
            PaymentResponseDTO dto = new PaymentResponseDTO();
            dto.setId(payment.getId());
            dto.setAmount(payment.getAmount());
            dto.setDescription(payment.getDescription());
            dto.setStatus(payment.getStatus());
            return dto;
        }).toList();
    }

    // 3. 按 ID 获取支付记录
    @Operation(summary = "根据 ID 获取支付记录", description = "根据支付记录的 ID 查询详细信息")
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // 4. 更新支付记录
    @Operation(summary = "更新支付记录", description = "根据 ID 更新已有的支付记录信息")
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(
            @Parameter(description = "要更新的支付记录 ID") @PathVariable Long id,
            @RequestBody Payment payment) {
        return ResponseEntity.ok(paymentService.updatePayment(id, payment));
    }

    // 5. 删除支付记录
    @Operation(summary = "删除支付记录", description = "根据 ID 删除指定的支付记录")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @Parameter(description = "要删除的支付记录 ID") @PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }


}

package com.smartpay.paymentservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.smartpay.paymentservice.enums.PaymentChannel;



@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String payer;

    private String payee;

    private String status; // e.g. CREATED, SUCCESS, FAILED

    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "0.01", message = "金额必须大于 0 元")
    private Double amount;

    @NotBlank(message = "描述不能为空")
    private String description;

    @NotNull(message = "支付渠道不能为空")
    @Enumerated(EnumType.STRING)
    private PaymentChannel channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 🟢 新增：发起此支付的用户

    private LocalDateTime timestamp;


}

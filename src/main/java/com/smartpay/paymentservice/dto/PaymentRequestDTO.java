package com.smartpay.paymentservice.dto;

import com.smartpay.paymentservice.enums.PaymentChannel;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {

    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "0.01", message = "金额必须大于 0 元")
    private Double amount;

    @NotBlank(message = "描述不能为空")
    private String description;

    @NotNull(message = "支付渠道不能为空")
    private PaymentChannel channel;

}

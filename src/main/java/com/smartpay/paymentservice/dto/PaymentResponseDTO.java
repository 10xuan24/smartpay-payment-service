package com.smartpay.paymentservice.dto;

import com.smartpay.paymentservice.enums.PaymentChannel;
import lombok.Data;

@Data
public class PaymentResponseDTO {

    private Long id;
    private Double amount;
    private String description;
    private String status;
    private PaymentChannel channel;

}

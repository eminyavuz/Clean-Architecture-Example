package com.example.clean_architecture_example.adapter.web.dto.request;
import jakarta.validation.constraints.Min;
public record AddProductToOrderRequest(
        int  productId,
        @Min(1)
        int quantity
)
{

}

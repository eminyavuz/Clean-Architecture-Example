package com.example.clean_architecture_example.adapter.web.dto.request;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddProductToOrderRequest(
       @NotNull
        int  productId,
        @Min(1)
        int quantity
)
{

}

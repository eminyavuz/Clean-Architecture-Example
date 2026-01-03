package com.example.clean_architecture_example.adapter.web.dto;
import jakarta.validation.constraints.Min;
public record AddProductRequest(
        int  productId,
        @Min(1)
        int quantity
)
{

}

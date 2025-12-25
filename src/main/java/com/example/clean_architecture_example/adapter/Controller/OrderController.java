package com.example.clean_architecture_example.adapter.Controller;

import com.example.clean_architecture_example.application.usecase.CreateOrderUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController
{
    private final CreateOrderUseCase createOrderUseCase;

    public OrderController(CreateOrderUseCase useCase)
    {
        this.createOrderUseCase=useCase;
    }
    @PostMapping("/orders")
    public ResponseEntity<Integer> createOrder(){
        int orderId= createOrderUseCase.execute();
        return ResponseEntity.ok(orderId);
    }
}

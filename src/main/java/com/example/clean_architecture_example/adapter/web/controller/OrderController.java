package com.example.clean_architecture_example.adapter.web.controller;

import com.example.clean_architecture_example.adapter.web.dto.request.AddProductToOrderRequest;
import com.example.clean_architecture_example.application.usecase.order.AddProductToOrderUseCase;
import com.example.clean_architecture_example.application.usecase.order.CreateOrderUseCase;
import com.example.clean_architecture_example.application.usecase.order.StartOrderProgressUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    // Controller classes act like a dispatcher
    private final CreateOrderUseCase createOrderUseCase;
    private final AddProductToOrderUseCase addProductToOrderUseCase;
    private final StartOrderProgressUseCase startOrderProgressUseCase;

    public OrderController(CreateOrderUseCase createOrderUseCase, AddProductToOrderUseCase addProductToOrderUseCase, StartOrderProgressUseCase startOrderProgressUseCase) {
        this.addProductToOrderUseCase = addProductToOrderUseCase;
        this.startOrderProgressUseCase = startOrderProgressUseCase;
        this.createOrderUseCase = createOrderUseCase;
    }

    @PostMapping
    public ResponseEntity<Integer> createOrder() {
        int orderId = createOrderUseCase.execute();
        return ResponseEntity.ok(orderId);
    }
    @PostMapping("/{orderId}/items")
    public ResponseEntity<Void> addProduct(
            @PathVariable int orderId,
            @Valid @RequestBody AddProductToOrderRequest request
            )
    {
        addProductToOrderUseCase.execute(
                orderId,
                request.productId(),
                request.quantity()
        );
        return  ResponseEntity.ok().build();
    }
    @PostMapping("/{orderId}/start")
    public ResponseEntity<Void> startProgress(@PathVariable int orderId)
    {
        startOrderProgressUseCase.execute(orderId);
        return  ResponseEntity.ok().build();
    }

}

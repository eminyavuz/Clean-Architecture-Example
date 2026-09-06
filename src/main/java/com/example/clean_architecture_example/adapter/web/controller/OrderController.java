package com.example.clean_architecture_example.adapter.web.controller;

import com.example.clean_architecture_example.adapter.web.dto.request.AddProductToOrderRequest;
import com.example.clean_architecture_example.adapter.web.dto.response.OrderResponse;
import com.example.clean_architecture_example.adapter.web.mapper.OrderWebMapper;
import com.example.clean_architecture_example.application.usecase.order.AddProductToOrderUseCase;
import com.example.clean_architecture_example.application.usecase.order.CreateOrderUseCase;
import com.example.clean_architecture_example.application.usecase.order.GetOrderUseCase;
import com.example.clean_architecture_example.application.usecase.order.ListOrdersUseCase;
import com.example.clean_architecture_example.application.usecase.order.StartOrderProgressUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final AddProductToOrderUseCase addProductToOrderUseCase;
    private final StartOrderProgressUseCase startOrderProgressUseCase;
    private final GetOrderUseCase getOrderUseCase;
    private final ListOrdersUseCase listOrdersUseCase;

    public OrderController(
            CreateOrderUseCase createOrderUseCase,
            AddProductToOrderUseCase addProductToOrderUseCase,
            StartOrderProgressUseCase startOrderProgressUseCase,
            GetOrderUseCase getOrderUseCase,
            ListOrdersUseCase listOrdersUseCase
    ) {
        this.addProductToOrderUseCase = addProductToOrderUseCase;
        this.startOrderProgressUseCase = startOrderProgressUseCase;
        this.createOrderUseCase = createOrderUseCase;
        this.getOrderUseCase = getOrderUseCase;
        this.listOrdersUseCase = listOrdersUseCase;
    }

    @GetMapping
    public List<OrderResponse> listOrders() {
        return listOrdersUseCase.execute().stream()
                .map(OrderWebMapper::toResponse)
                .toList();
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable int orderId) {
        return OrderWebMapper.toResponse(getOrderUseCase.execute(orderId));
    }

    @PostMapping("/create")
    public ResponseEntity<Integer> createOrder() {
        int orderId = createOrderUseCase.execute();
        return ResponseEntity.ok(orderId);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderResponse> addProduct(
            @PathVariable int orderId,
            @Valid @RequestBody AddProductToOrderRequest request
    ) {
        addProductToOrderUseCase.execute(orderId, request.productId(), request.quantity());
        return ResponseEntity.ok(OrderWebMapper.toResponse(getOrderUseCase.execute(orderId)));
    }

    @PostMapping("/{orderId}/start")
    public ResponseEntity<OrderResponse> startProgress(@PathVariable int orderId) {
        startOrderProgressUseCase.execute(orderId);
        return ResponseEntity.ok(OrderWebMapper.toResponse(getOrderUseCase.execute(orderId)));
    }
}

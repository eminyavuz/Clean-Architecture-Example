package com.example.clean_architecture_example.adapter.web.controller;

import com.example.clean_architecture_example.adapter.web.dto.request.CreateProductRequest;
import com.example.clean_architecture_example.adapter.web.dto.request.UpdateProductPriceRequest;
import com.example.clean_architecture_example.adapter.web.dto.request.UpdateProductStockRequest;
import com.example.clean_architecture_example.adapter.web.dto.response.ProductResponse;
import com.example.clean_architecture_example.adapter.web.mapper.ProductWebMapper;
import com.example.clean_architecture_example.application.usecase.product.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ActivateProductUseCase activateProductUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final DeactivateProductUseCase deactivateProductUseCase;
    private final GetProductUseCase getProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final UpdateProductPriceUseCase updateProductPriceUseCase;

    public ProductController
            (
            ActivateProductUseCase activateProductUseCase,
            CreateProductUseCase createProductUseCase,
            DeactivateProductUseCase deactivateProductUseCase,
            GetProductUseCase getProductUseCase,
            ListProductsUseCase listProductsUseCase,
            UpdateProductStockUseCase updateProductStockUseCase,
            UpdateProductPriceUseCase updateProductPriceUseCase
            )
    {
        this.activateProductUseCase= activateProductUseCase;
        this.deactivateProductUseCase= deactivateProductUseCase;
        this.createProductUseCase=createProductUseCase;
        this.getProductUseCase=getProductUseCase;
        this.listProductsUseCase = listProductsUseCase;
        this.updateProductPriceUseCase=updateProductPriceUseCase;
        this.updateProductStockUseCase=updateProductStockUseCase;
    }

    @GetMapping
    public List<ProductResponse> listProducts() {
        return listProductsUseCase.execute().stream()
                .map(ProductWebMapper::toResponse)
                .toList();
    }

    @PostMapping("/create")
    public ProductResponse create(@RequestBody CreateProductRequest request )
    {
        int id= createProductUseCase.execute(

                request.getProductName(),
                request.getPrice(),
                request.getDescription(),
                request.getStock(),
                request.getIsActive()
        );

        return ProductWebMapper.toResponse(getProductUseCase.execute(id));
    }
    @GetMapping("/{productId}")
    public ProductResponse getProduct( @PathVariable int productId)
    {
        return ProductWebMapper.toResponse(getProductUseCase.execute(productId));
    }

    @PutMapping("/{productId}/price")
    public void updateProductPrice(@PathVariable int productId,
                                   @RequestBody UpdateProductPriceRequest request)
    {
       updateProductPriceUseCase.execute(productId,request.getNewPrice());
    }

    @PutMapping("/{productId}/stock")
    public void updateProductStock(@PathVariable int productId,
                                   @RequestBody UpdateProductStockRequest request)
    {
        updateProductStockUseCase.execute(productId,request.getNewStock());
    }

    @PutMapping("/{productId}/activate")
    public  void activateProduct(@PathVariable int productId)
    {
        activateProductUseCase.execute(productId);
    }

    @PutMapping("/{productId}/deactivate")
    public  void  deactivateProduct(@PathVariable int productId)
    {
        deactivateProductUseCase.execute(productId);
    }

}

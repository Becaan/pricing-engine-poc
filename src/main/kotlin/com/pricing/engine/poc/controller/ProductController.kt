package com.pricing.engine.poc.controller

import com.pricing.engine.poc.model.Product
import com.pricing.engine.poc.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAllProducts(): List<Product> = productService.getAllProducts()

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: Long): ResponseEntity<Product> {
        val product = productService.getProductById(id)
        return if (product != null) {
            ResponseEntity.ok(product)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val createdProduct = productService.createProduct(product)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct)
    }
    @PatchMapping("/{id}/calculate-discount")
    fun calculateDiscount(
        @PathVariable id: Long,
    ): ResponseEntity<Product> {
        val product = productService.calculateDiscount(id)
        return if (product != null) {
            ResponseEntity.ok(product)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: Long): ResponseEntity<Void> {
        return if (productService.deleteProduct(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
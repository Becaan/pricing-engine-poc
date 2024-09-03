package com.pricing.engine.poc.repository

import com.pricing.engine.poc.model.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {

}
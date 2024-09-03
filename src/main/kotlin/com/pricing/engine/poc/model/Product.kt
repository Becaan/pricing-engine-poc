package com.pricing.engine.poc.model

import jakarta.persistence.*


@Entity
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,

    val name: String? = null,
    val price: Int? = null,
    var discount: Int? = null


) {

    fun setDiscount(dis: Int) {
        discount = dis
    }
}
package com.pricing.engine.poc.model

import jakarta.persistence.*

@Entity
class Rule(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,

    @Column(name = "ifcondition")
    val ifcondition: String,
    @Column(name = "thencondition")
    val thencondition: String,
    @Column(name = "version")
    val version: Int,
) {}
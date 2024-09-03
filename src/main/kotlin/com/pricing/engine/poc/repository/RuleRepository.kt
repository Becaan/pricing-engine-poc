package com.pricing.engine.poc.repository

import com.pricing.engine.poc.model.Product
import com.pricing.engine.poc.model.Rule
import org.springframework.data.jpa.repository.JpaRepository

interface RuleRepository : JpaRepository<Rule, Long> {

}
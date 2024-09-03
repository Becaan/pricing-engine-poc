package com.pricing.engine.poc.controller

import com.pricing.engine.poc.model.Rule
import com.pricing.engine.poc.repository.RuleRepository
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/rules")
class RuleController(
    private val ruleRepository: RuleRepository
) {

    @PostMapping
    fun createRule(@RequestBody rule: Rule): Rule {
        return ruleRepository.save(rule)
    }

    @GetMapping
    fun getRules(): List<Rule> {
        val ruleList: List<Rule> = ruleRepository.findAll()
        return ruleList
    }
}
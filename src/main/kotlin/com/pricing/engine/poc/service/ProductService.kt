package com.pricing.engine.poc.service

import com.pricing.engine.poc.config.DroolsConfig
import com.pricing.engine.poc.model.Product
import com.pricing.engine.poc.model.Rule
import com.pricing.engine.poc.repository.ProductRepository
import com.pricing.engine.poc.repository.RuleRepository
import org.drools.template.ObjectDataCompiler
import org.kie.api.KieServices
import org.kie.api.io.Resource
import org.kie.api.io.ResourceType
import org.kie.internal.utils.KieHelper
import org.springframework.stereotype.Service


@Service
class ProductService(private val productRepository: ProductRepository,
    private val ruleRepository: RuleRepository) {

    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun getProductById(id: Long): Product? = productRepository.findById(id).orElse(null)

    fun createProduct(product: Product): Product = productRepository.save(product)

    fun calculateDiscount(id: Long): Product? {
        val product = productRepository.findById(id).orElse(null) ?: return null
        val ruleAttributes = ArrayList<Rule?>()
        ruleRepository.findAll().forEach(ruleAttributes::add)

        val compiler = ObjectDataCompiler()
        val generatedDRL = compiler.compile(
            ruleAttributes,
            Thread.currentThread().contextClassLoader.getResourceAsStream(DroolsConfig.RULES_TEMPLATE_FILE)
        )
        val kieServices = KieServices.Factory.get()

        val kieHelper = KieHelper()

        //multiple such resoures/rules can be added
        val b1 = generatedDRL.toByteArray()
        val resource1: Resource = kieServices.resources.newByteArrayResource(b1)
        kieHelper.addResource(resource1, ResourceType.DRL)

        val kieBase = kieHelper.build()

        val kieSession = kieBase.newKieSession()
        kieSession.insert(product)
        val numberOfRulesFired = kieSession.fireAllRules()
        kieSession.dispose()

        return productRepository.save(product)
    }

    fun deleteProduct(id: Long): Boolean {
        return if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
            true
        } else {
            false
        }
    }
}
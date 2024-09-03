package com.pricing.engine.poc.config

import org.kie.api.KieServices
import org.kie.api.runtime.KieContainer
import org.kie.internal.io.ResourceFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class DroolsConfig {
    companion object {
        val RULES_CUSTOMER_RULES_DRL = "rules/customer-discount.drl"
        val RULES_TEMPLATE_FILE = "rules/discount-template.drl"
    }
    private val kieServices = KieServices.Factory.get()

    @Bean
    fun kieContainer(): KieContainer {
        val kieFileSystem = kieServices.newKieFileSystem()
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_CUSTOMER_RULES_DRL))
        val kb = kieServices.newKieBuilder(kieFileSystem)
        kb.buildAll()
        val kieModule = kb.kieModule
        val kieContainer = kieServices.newKieContainer(kieModule.releaseId)
        return kieContainer
    }
}
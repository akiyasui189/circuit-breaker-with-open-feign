package io.spring.start.sample.circuitbreaker.configurations

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class LoggingConfig {

    @Bean
    @Scope("prototype")
    fun getLogger (injectionPoint: InjectionPoint) : Logger {
        return LoggerFactory.getLogger(
                // constructor injection
                injectionPoint.methodParameter?.containingClass
                        // or field injection
                        ?: injectionPoint.field?.declaringClass
        )
    }
}
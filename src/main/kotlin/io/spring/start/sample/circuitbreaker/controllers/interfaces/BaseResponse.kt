package io.spring.start.sample.circuitbreaker.controllers.interfaces

data class BaseResponse (
        val status: String,
        val code: String,
        val message: String,
        val processingTimeSeconds: Double,
        val isCircuitBreakerOpen: Boolean
)
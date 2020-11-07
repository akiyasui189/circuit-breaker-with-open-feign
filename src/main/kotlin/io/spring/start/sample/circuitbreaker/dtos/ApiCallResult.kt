package io.spring.start.sample.circuitbreaker.dtos

data class ApiCallResult (
        val isSucceed : Boolean,
        val isFallback: Boolean,
        val isCircuitBreaker: Boolean,
        val processingTimeSeconds: Float
)
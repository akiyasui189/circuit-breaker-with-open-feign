package io.spring.start.sample.circuitbreaker.controllers.interfaces

data class StubBaseResponse (
    var status: String,
    var code: String,
    var message: String,
    var processingTimeSeconds: Float
)
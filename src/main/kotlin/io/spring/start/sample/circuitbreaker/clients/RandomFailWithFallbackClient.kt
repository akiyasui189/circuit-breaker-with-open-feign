package io.spring.start.sample.circuitbreaker.clients

import io.spring.start.sample.circuitbreaker.clients.fallback.RandomFailFallback
import io.spring.start.sample.circuitbreaker.controllers.interfaces.StubBaseResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
        name = "random-fail-fallback-client",
        url = "\${feign.client.config.random-fail-client.base-url}",
        fallback = RandomFailFallback::class)
interface RandomFailWithFallbackClient {
    @GetMapping("/random-fail")
    fun randomFail() : ResponseEntity<StubBaseResponse>
}
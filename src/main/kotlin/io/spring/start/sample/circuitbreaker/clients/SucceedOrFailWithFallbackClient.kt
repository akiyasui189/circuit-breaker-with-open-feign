package io.spring.start.sample.circuitbreaker.clients

import io.spring.start.sample.circuitbreaker.clients.fallback.SucceedOrFailFallback
import io.spring.start.sample.circuitbreaker.controllers.interfaces.StubBaseResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
        name = "succeed-or-fail-fallback-client",
        url = "\${feign.client.config.succeed-or-fail-client.base-url}",
        fallback = SucceedOrFailFallback::class
)
interface SucceedOrFailWithFallbackClient {
    @GetMapping("/succeed-or-fail")
    fun succeedOrFail(@RequestParam("isFail") isFail: Boolean) : ResponseEntity<StubBaseResponse>
}
package io.spring.start.sample.circuitbreaker.clients

import io.spring.start.sample.circuitbreaker.controllers.interfaces.StubBaseResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(contextId = "wait-time-v3-client", name = "wait-time-v3-client", url = "\${feign.client.config.wait-time-v3-client.base-url}")
interface WaitTimeClientV3 {
    @GetMapping("/wait-time")
    fun waitTime(@RequestParam("seconds") seconds: Long) : ResponseEntity<StubBaseResponse>
}
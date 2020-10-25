package io.spring.start.sample.circuitbreaker.clients

import io.spring.start.sample.circuitbreaker.controllers.interfaces.StubBaseResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
        contextId = "okhttp-wait-time-client",
        name = "okhttp-wait-time-client",
        url = "\${feign.client.config.okhttp-wait-time-client.base-url}"
)
interface OkHttpWaitTimeClient {
    @GetMapping("/wait-time")
    fun waitTime(@RequestParam("seconds") seconds: Long) : ResponseEntity<StubBaseResponse>
}
package io.spring.start.sample.circuitbreaker.clients

import io.spring.start.sample.circuitbreaker.controllers.interfaces.StubBaseResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "always-fail-client", url = "\${feign.client.config.always-fail-client.base-url}")
interface AlwaysFailClient {
    @GetMapping("/always-fail")
    fun alwaysFail() : ResponseEntity<StubBaseResponse>
}
package io.spring.start.sample.circuitbreaker.clients.fallback

import io.spring.start.sample.circuitbreaker.clients.SucceedOrFailWithFallbackClient
import io.spring.start.sample.circuitbreaker.controllers.interfaces.StubBaseResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class SucceedOrFailFallback : SucceedOrFailWithFallbackClient {
    override fun succeedOrFail(isFail: Boolean): ResponseEntity<StubBaseResponse> {
        return ResponseEntity.ok(
                StubBaseResponse(
                        "S",
                        "00000000",
                        "SUCCESS",
                        0f
                )
        )
    }
}
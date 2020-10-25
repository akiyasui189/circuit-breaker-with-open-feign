package io.spring.start.sample.circuitbreaker

import io.spring.start.sample.circuitbreaker.clients.AlwaysFailClient
import io.spring.start.sample.circuitbreaker.clients.AlwaysFailWithFallbackClient
import io.spring.start.sample.circuitbreaker.clients.OkHttpWaitTimeClient
import io.spring.start.sample.circuitbreaker.clients.RandomFailClient
import io.spring.start.sample.circuitbreaker.clients.RandomFailWithFallbackClient
import io.spring.start.sample.circuitbreaker.clients.SucceedOrFailClient
import io.spring.start.sample.circuitbreaker.clients.SucceedOrFailWithFallbackClient
import io.spring.start.sample.circuitbreaker.clients.WaitTimeClient
import io.spring.start.sample.circuitbreaker.clients.WaitTimeClientV2
import io.spring.start.sample.circuitbreaker.clients.WaitTimeClientV3
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableCircuitBreaker
@EnableFeignClients(
        clients = [
            AlwaysFailClient::class,
            AlwaysFailWithFallbackClient::class,
            //OkHttpWaitTimeClient::class,
            RandomFailClient::class,
            RandomFailWithFallbackClient::class,
            SucceedOrFailClient::class,
            SucceedOrFailWithFallbackClient::class,
            WaitTimeClient::class,
            WaitTimeClientV2::class,
            WaitTimeClientV3::class
        ]
)
@SpringBootApplication
class CircuitBreakerApplication

fun main(args: Array<String>) {
    runApplication<CircuitBreakerApplication>(*args)
}

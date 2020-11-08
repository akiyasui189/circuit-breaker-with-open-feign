package io.spring.start.sample.circuitbreaker.configurations

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiter
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.time.Duration

@Configuration
class ResilienceConfig {

    @Value("\${resilience.circuit-breaker.sample.failure-rate}")
    val sampleCircuitBreakerFailureRate : Float = 50f
    @Value("\${resilience.circuit-breaker.sample.slow-call-millis}")
    val sampleCircuitBreakerSlowCallMillis : Long = 3000L
    @Value("\${resilience.circuit-breaker.sample.slow-call-rate}")
    val sampleCircuitBreakerSlowCallRate : Float = 50f
    @Value("\${resilience.circuit-breaker.sample.ring-buffer-size}")
    val sampleCircuitBreakerRingBufferSize : Int = 100
    @Value("\${resilience.circuit-breaker.sample.minimum-number-of-calls}")
    val sampleCircuitBreakerMinimumNumberOfCalls : Int = 5
    @Value("\${resilience.circuit-breaker.sample.ring-buffer-size-in-half}")
    val sampleCircuitBreakerRingBufferSizeInHalfOpen : Int = 10
    @Value("\${resilience.circuit-breaker.sample.wait-millis-in-open}")
    val sampleCircuitBreakerWaitMillisInOpen : Long = 100000L

    @Value("\${resilience.time-limiter.sample.timeout-millis}")
    val sampleTimeLimiterTimeoutMillis = 500L

    @Primary
    @Bean
    fun defaultCircuitBreaker () : CircuitBreaker {
        return CircuitBreaker.ofDefaults("default");
    }

    @Bean("sampleCircuitBreaker")
    fun sampleCircuitBreaker () : CircuitBreaker {
        return CircuitBreaker.of("sampleCircuitBreaker", CircuitBreakerConfig.custom()
                // failure rate
                .failureRateThreshold(sampleCircuitBreakerFailureRate)
                // slow rate
                .slowCallDurationThreshold(Duration.ofMillis(sampleCircuitBreakerSlowCallMillis))
                .slowCallRateThreshold(sampleCircuitBreakerSlowCallRate)
                // ringBufferSizeInClosedState
                .slidingWindow(
                        sampleCircuitBreakerRingBufferSize,
                        sampleCircuitBreakerMinimumNumberOfCalls,
                        CircuitBreakerConfig.SlidingWindowType.COUNT_BASED
                )
                // ringBufferSizeInHalfOpenState
                .permittedNumberOfCallsInHalfOpenState(sampleCircuitBreakerRingBufferSizeInHalfOpen)
                .waitDurationInOpenState(Duration.ofMillis(sampleCircuitBreakerWaitMillisInOpen))
                .automaticTransitionFromOpenToHalfOpenEnabled(false)
                .recordExceptions(
                        RuntimeException::class.java
                )
                //.ignoreExceptions()
                .build())
    }

    @Primary
    @Bean
    fun defaultTimeLimiter () : TimeLimiter {
        return TimeLimiter.ofDefaults("default")
    }

    @Bean("sampleTimeLimiter")
    fun sampleTimeLimiter () : TimeLimiter {
        return TimeLimiter.of(TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(sampleTimeLimiterTimeoutMillis))
                .cancelRunningFuture(true)
                .build())
    }

}
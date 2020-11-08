package io.spring.start.sample.circuitbreaker.services

import io.github.resilience4j.circuitbreaker.CircuitBreaker
import io.github.resilience4j.timelimiter.TimeLimiter
import io.spring.start.sample.circuitbreaker.dtos.ApiCallResult
import io.spring.start.sample.circuitbreaker.repositories.SampleRepository
import io.vavr.control.Try
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class ResilienceSampleService (
        @Qualifier("sampleCircuitBreaker")
        val circuitBreaker: CircuitBreaker,
        @Qualifier("sampleTimeLimiter")
        val timeLimiter: TimeLimiter,
        val sampleRepository: SampleRepository,
        val logger: Logger) {
    fun getError() : ApiCallResult {
        return Try.ofSupplier(
                CircuitBreaker.decorateSupplier(circuitBreaker, sampleRepository::getError)
        )
                .recover(
                        RuntimeException::class.java,
                        ApiCallResult(false, true, true, 0f))
                .get()
    }
    fun randomFail() : ApiCallResult {
        circuitBreaker.run {
            return sampleRepository.randomFail()
        }
    }
    fun succeedOrFail(isFail: Boolean) : ApiCallResult {
        circuitBreaker.run {
            return sampleRepository.succeedOrFail(isFail)
        }
    }
    fun waitTime(seconds: Long) : ApiCallResult {
        timeLimiter.run {
            return sampleRepository.waitTime(seconds)
        }
    }
}
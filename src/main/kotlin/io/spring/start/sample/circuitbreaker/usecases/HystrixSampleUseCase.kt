package io.spring.start.sample.circuitbreaker.usecases

import io.spring.start.sample.circuitbreaker.dtos.ApiCallResult
import io.spring.start.sample.circuitbreaker.services.HystrixSampleService
import io.spring.start.sample.circuitbreaker.services.SampleService
import org.springframework.stereotype.Component

@Component
class HystrixSampleUseCase(val hystrixSampleService: HystrixSampleService) {
    fun getError() : ApiCallResult {
        return hystrixSampleService.getError()
    }
    fun randomFail() : ApiCallResult {
        return hystrixSampleService.randomFail()
    }
    fun succeedOrFail(isFail: Boolean) : ApiCallResult {
        return hystrixSampleService.succeedOrFail(isFail)
    }
    fun waitTime(seconds: Long) : ApiCallResult {
        return hystrixSampleService.waitTime(seconds)
    }
}
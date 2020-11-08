package io.spring.start.sample.circuitbreaker.usecases

import io.spring.start.sample.circuitbreaker.dtos.ApiCallResult
import io.spring.start.sample.circuitbreaker.services.ResilienceSampleService
import org.springframework.stereotype.Component

@Component
class ResilienceSampleUseCase (val resilienceSampleService: ResilienceSampleService) {
    fun getError() : ApiCallResult {
        return resilienceSampleService.getError()
    }
    fun randomFail() : ApiCallResult {
        return resilienceSampleService.randomFail()
    }
    fun succeedOrFail(isFail: Boolean) : ApiCallResult {
        return resilienceSampleService.succeedOrFail(isFail)
    }
    fun waitTime(seconds: Long) : ApiCallResult {
        return resilienceSampleService.waitTime(seconds)
    }
}
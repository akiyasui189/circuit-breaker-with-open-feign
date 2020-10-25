package io.spring.start.sample.circuitbreaker.usecases

import io.spring.start.sample.circuitbreaker.dtos.ApiCallResult
import io.spring.start.sample.circuitbreaker.services.SampleService
import org.springframework.stereotype.Component

@Component
class SampleUseCase(val sampleService: SampleService) {
    fun getError() : ApiCallResult {
        return sampleService.getError()
    }
    fun getStubResponse() : ApiCallResult {
        return sampleService.getStubResponse()
    }
    /*
    fun okHttpClientWaitTime(seconds: Long) : ApiCallResult {
        return sampleService.okHttpClientWaitTime(seconds)
    }
     */
    fun randomFail() : ApiCallResult {
        return sampleService.randomFail()
    }
    fun randomFailWithFallback() : ApiCallResult {
        return sampleService.randomFailWithFallback()
    }
    fun succeedOrFail(isFail: Boolean) : ApiCallResult {
        return sampleService.succeedOrFail(isFail)
    }
    fun succeedOrFailWithFallback(isFail: Boolean) : ApiCallResult {
        return sampleService.succeedOrFailWithFallback(isFail)
    }
    fun waitTime(seconds: Long) : ApiCallResult {
        return sampleService.waitTime(seconds)
    }
    fun waitTimeZeroSeconds() : ApiCallResult {
        return sampleService.waitTimeZeroSeconds()
    }
    fun waitTimeOneSeconds() : ApiCallResult {
        return sampleService.waitTimeOneSeconds()
    }
    fun waitTimeTwoSeconds() : ApiCallResult {
        return sampleService.waitTimeTwoSeconds()
    }
    fun waitTimeThreeSeconds() : ApiCallResult {
        return sampleService.waitTimeThreeSeconds()
    }
    fun waitTimeV2(seconds:Long) : ApiCallResult {
        return sampleService.waitTimeV2(seconds)
    }
    fun waitTimeV3(seconds:Long) : ApiCallResult {
        return sampleService.waitTimeV3(seconds)
    }
}
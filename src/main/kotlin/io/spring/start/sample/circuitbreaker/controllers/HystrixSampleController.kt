package io.spring.start.sample.circuitbreaker.controllers

import io.spring.start.sample.circuitbreaker.controllers.interfaces.BaseResponse
import io.spring.start.sample.circuitbreaker.dtos.ApiCallResult
import io.spring.start.sample.circuitbreaker.usecases.HystrixSampleUseCase
import io.spring.start.sample.circuitbreaker.usecases.SampleUseCase
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException

@RestController
@RequestMapping("/hystrix")
class HystrixSampleController (val hystrixSampleUseCase: HystrixSampleUseCase, val logger: Logger) {

    @GetMapping("/error")
    fun getProcessingTime() : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = hystrixSampleUseCase.getError()
        if (!result.isSucceed && !result.isFallback) {
            return BaseResponse(
                    "F",
                    "01001001",
                    "NG",
                    (System.currentTimeMillis() - startTime).toDouble() / 1000.toDouble(),
                    result.isFallback,
                    result.isCircuitBreaker
            )
        }
        return BaseResponse(
                "S",
                "00000000",
                "OK",
                (System.currentTimeMillis() - startTime).toDouble() / 1000.toDouble(),
                result.isFallback,
                result.isCircuitBreaker
        )
    }

    @GetMapping("/random-fail")
    fun randomFail() : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = hystrixSampleUseCase.randomFail()
        if (!result.isSucceed && !result.isFallback) {
            logger.error("processing time: " + result.processingTimeSeconds)
            throw RuntimeException("Read Time Out")
        }
        return BaseResponse(
                "S",
                "00000000",
                "OK",
                (System.currentTimeMillis() - startTime).toDouble() / 1000.toDouble(),
                result.isFallback,
                result.isCircuitBreaker
        )
    }

    @GetMapping("/succeed-or-fail")
    fun succeedOrFail(@RequestParam("fail") isFail: Boolean) : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = hystrixSampleUseCase.succeedOrFail(isFail)
        if (!result.isSucceed && !result.isFallback) {
            logger.error("processing time: " + result.processingTimeSeconds)
            throw RuntimeException("Read Time Out")
        }
        return BaseResponse(
                "S",
                "00000000",
                "OK",
                (System.currentTimeMillis() - startTime).toDouble() / 1000.toDouble(),
                result.isFallback,
                result.isCircuitBreaker
        )
    }

    @GetMapping("/wait-time")
    fun waitTime(@RequestParam("seconds") seconds: Long) : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = hystrixSampleUseCase.waitTime(seconds)
        if (!result.isSucceed && !result.isFallback) {
            logger.error("processing time: " + result.processingTimeSeconds)
            throw RuntimeException("Read Time Out")
        }
        return BaseResponse(
                "S",
                "00000000",
                "OK",
                (System.currentTimeMillis() - startTime).toDouble() / 1000.toDouble(),
                result.isFallback,
                result.isCircuitBreaker
        )
    }

}
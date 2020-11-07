package io.spring.start.sample.circuitbreaker.controllers

import io.spring.start.sample.circuitbreaker.controllers.interfaces.BaseResponse
import io.spring.start.sample.circuitbreaker.dtos.ApiCallResult
import io.spring.start.sample.circuitbreaker.usecases.SampleUseCase
import org.slf4j.Logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.RuntimeException

@RestController
@RequestMapping("/sample")
class SampleController (val useCase: SampleUseCase, val logger: Logger) {

    @GetMapping("/error")
    fun getProcessingTime() : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.getError()
        if (!result.isSucceed) {
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

    @GetMapping("/error-with-fallback")
    fun getStubResponse() : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.getStubResponse()
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

    @GetMapping("/random-fail")
    fun randomFail() : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.randomFail()
        if (!result.isSucceed) {
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

    @GetMapping("/random-fail-with-fallback")
    fun randomFailWithFallback() : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.randomFailWithFallback()
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
        val result : ApiCallResult = useCase.succeedOrFail(isFail)
        if (!result.isSucceed) {
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

    @GetMapping("/succeed-or-fail-with-fallback")
    fun succeedOrFailWithFallback(@RequestParam("fail") isFail: Boolean) : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.succeedOrFailWithFallback(isFail)
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

    /*
    @GetMapping("/okhttp-wait-time")
    fun okHttpClientWaitTime(@RequestParam("seconds") seconds: Long) : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.okHttpClientWaitTime(seconds)
        if (!result.isSucceed) {
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
     */

    @GetMapping("/wait-time")
    fun waitTime(@RequestParam("seconds") seconds: Long) : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.waitTime(seconds)
        if (!result.isSucceed) {
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

    @GetMapping("/wait-time-0sec")
    fun waitTimeZeroSeconds() : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.waitTimeZeroSeconds()
        if (!result.isSucceed) {
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

    @GetMapping("/wait-time-1sec")
    fun waitTimeOneSeconds() : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.waitTimeOneSeconds()
        if (!result.isSucceed) {
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

    @GetMapping("/wait-time-2sec")
    fun waitTimeTwoSecondsError() : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.waitTimeTwoSeconds()
        if (!result.isSucceed) {
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

    @GetMapping("/wait-time-3sec")
    fun waitTimeThreeSecondsError() : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.waitTimeThreeSeconds()
        if (!result.isSucceed) {
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

    @GetMapping("/wait-time-v2")
    fun waitTimeV2(@RequestParam("seconds") seconds: Long) : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.waitTimeV2(seconds)
        if (!result.isSucceed) {
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

    @GetMapping("/wait-time-v3")
    fun waitTimeV3(@RequestParam("seconds") seconds: Long) : BaseResponse {
        val startTime = System.currentTimeMillis()
        val result : ApiCallResult = useCase.waitTimeV3(seconds)
        if (!result.isSucceed) {
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
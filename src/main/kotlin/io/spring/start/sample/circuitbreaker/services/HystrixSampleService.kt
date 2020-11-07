package io.spring.start.sample.circuitbreaker.services

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager
import io.spring.start.sample.circuitbreaker.dtos.ApiCallResult
import io.spring.start.sample.circuitbreaker.repositories.SampleRepository
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class HystrixSampleService (val sampleRepository: SampleRepository, val logger: Logger) {

    // Circuit Breaker by SEMAPHORE
    @HystrixCommand(
            groupKey = "HystrixSample",
            commandKey = "GetError",
            fallbackMethod = "getErrorFallback",
            commandProperties = [
                HystrixProperty(name = HystrixPropertiesManager.EXECUTION_TIMEOUT_ENABLED, value = "false"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED, value = "true"),
                HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_ENABLED, value = "true"),
                HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "SEMAPHORE"),
                HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS, value = "2"),
                HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS, value = "10000"),
                HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_TIME_IN_MILLISECONDS, value = "10000"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "10"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "5000"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50")
            ]
    )
    fun getError() : ApiCallResult {
        return sampleRepository.getError()
    }

    // Circuit Breaker by THREAD
    @HystrixCommand(
            groupKey = "HystrixSample",
            commandKey = "RandomFail",
            fallbackMethod = "randomFailFallback",
            commandProperties = [
                HystrixProperty(name = HystrixPropertiesManager.EXECUTION_TIMEOUT_ENABLED, value = "false"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED, value = "true"),
                HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_ENABLED, value = "true"),
                HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "THREAD"),
                HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS, value = "5000"),
                HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_TIME_IN_MILLISECONDS, value = "10000"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "10"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "5000"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50")
            ]
    )
    fun randomFail() : ApiCallResult {
        return sampleRepository.randomFail()
    }

    // Circuit Breaker by THREAD
    @HystrixCommand(
            groupKey = "HystrixSample",
            commandKey = "SucceedOrFail",
            fallbackMethod = "succeedOrFailFallback",
            commandProperties = [
                HystrixProperty(name = HystrixPropertiesManager.EXECUTION_TIMEOUT_ENABLED, value = "false"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED, value = "true"),
                HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_ENABLED, value = "true"),
                HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "THREAD"),
                HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_STATS_TIME_IN_MILLISECONDS, value = "5000"),
                HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_TIME_IN_MILLISECONDS, value = "10000"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "10"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "5000"),
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50")
            ]
    )
    fun succeedOrFail(isFail: Boolean) : ApiCallResult {
        return sampleRepository.succeedOrFail(isFail)
    }

    // TimeLimiter
    @HystrixCommand(
            groupKey = "HystrixSample",
            commandKey = "WaitTime",
            fallbackMethod = "waitTimeFallback",
            commandProperties = [
                HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ENABLED, value = "false"),
                HystrixProperty(name = HystrixPropertiesManager.METRICS_ROLLING_PERCENTILE_ENABLED, value = "false"),
                HystrixProperty(name = HystrixPropertiesManager.EXECUTION_TIMEOUT_ENABLED, value = "true"),
                HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "500")
            ]
    )
    fun waitTime(seconds: Long) : ApiCallResult {
        return sampleRepository.waitTime(seconds)
    }

    fun getErrorFallback() : ApiCallResult {
        // RODO
        return getFallbackResponse()
    }

    fun randomFailFallback() : ApiCallResult {
        return getFallbackResponse()
    }

    fun succeedOrFailFallback(isFail: Boolean) : ApiCallResult {
        return getFallbackResponse()
    }

    fun waitTimeFallback(seconds: Long) : ApiCallResult {
        return getFallbackResponse()
    }

    private fun getFallbackResponse() : ApiCallResult {
        logger.warn("Return Fallback Response.")
        return ApiCallResult(isSucceed = false, isFallback = true, isCircuitBreaker = true, processingTimeSeconds = 0.000f)
    }
}
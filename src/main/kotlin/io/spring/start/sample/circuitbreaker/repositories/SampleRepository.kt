package io.spring.start.sample.circuitbreaker.repositories

import feign.Feign
import feign.FeignException
import feign.Target
import feign.okhttp.OkHttpClient
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
import io.spring.start.sample.circuitbreaker.controllers.interfaces.StubBaseResponse
import io.spring.start.sample.circuitbreaker.dtos.ApiCallResult
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Repository
import java.lang.RuntimeException

@Repository
class SampleRepository (
        val alwaysFailClient: AlwaysFailClient,
        val alwaysFailWithFallbackClient: AlwaysFailWithFallbackClient,
        val randomFailClient: RandomFailClient,
        val randomFailWithFallbackClient: RandomFailWithFallbackClient,
        val succeedOrFailClient: SucceedOrFailClient,
        val succeedOrFailWithFallbackClient: SucceedOrFailWithFallbackClient,
        val waitTimeClient: WaitTimeClient,
        val waitTimeClientV2: WaitTimeClientV2,
        val waitTimeClientV3: WaitTimeClientV3,
        val logger: Logger
) {

    /* FIXME: Use OkHttpClient on FeignClient like this
    @Value("\${feign.client.config.okhttp-wait-time-client.base-url}")
    val okHttpWaitTimeClientUrl: String = ""
    private final val okHttpWaitTimeClient: OkHttpWaitTimeClient = Feign.builder()
            .client(OkHttpClient())
            .target(
                    Target.HardCodedTarget(
                            OkHttpWaitTimeClient::class.java,
                            "okhttp-wait-time-client",
                            "http://localhost:8081/stub"
                    )
            )

    fun okHttpClientWaitTime(seconds: Long) : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            okHttpWaitTimeClient.waitTime(seconds)
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }
     */

    fun getError() : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            alwaysFailClient.alwaysFail()
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun getStubResponse() : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            val stubBaseResponse = alwaysFailWithFallbackClient.alwaysFail().body
                    ?: StubBaseResponse("S", "00000000","SUCCESS",0.000f)
            if (stubBaseResponse.processingTimeSeconds.equals(0.000f)) {
                return ApiCallResult(
                        isSucceed = false,
                        isCircuitBreaker = true,
                        processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
                )
            }
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun randomFail() : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            randomFailClient.randomFail()
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun randomFailWithFallback() : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            val stubBaseResponse = randomFailWithFallbackClient.randomFail().body
                    ?: StubBaseResponse("S", "00000000","SUCCESS",0.000f)
            if (stubBaseResponse.processingTimeSeconds.equals(0.000f)) {
                return ApiCallResult(
                        isSucceed = false,
                        isCircuitBreaker = true,
                        processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
                )
            }
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun succeedOrFail(isFail: Boolean) : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            succeedOrFailClient.succeedOrFail(isFail)
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun succeedOrFailWithFallback(isFail: Boolean) : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            val stubBaseResponse = succeedOrFailWithFallbackClient.succeedOrFail(isFail).body
                    ?: StubBaseResponse("S", "00000000","SUCCESS",0.000f)
            if (stubBaseResponse.processingTimeSeconds.equals(0.000f)) {
                return ApiCallResult(
                        isSucceed = false,
                        isCircuitBreaker = true,
                        processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
                )
            }
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun waitTime(seconds: Long) : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            waitTimeClient.waitTime(seconds)
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun testReadTimeOutSettingCase0() : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            waitTimeClient.waitTime(0L)
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun testReadTimeOutSettingCase1() : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            waitTimeClient.waitTime(1L)
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            return ApiCallResult(
                    isSucceed = false,
                    isCircuitBreaker = false,
                    processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
            )
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun testReadTimeOutSettingCase2() : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            waitTimeClient.waitTime(2L)
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            return ApiCallResult(
                    isSucceed = false,
                    isCircuitBreaker = false,
                    processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
            )
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun testReadTimeOutSettingCase3() : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            waitTimeClient.waitTime(3L)
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            return ApiCallResult(
                    isSucceed = false,
                    isCircuitBreaker = false,
                    processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
            )
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun waitTimeV2(seconds: Long) : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            waitTimeClientV2.waitTime(seconds)
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

    fun waitTimeV3(seconds: Long) : ApiCallResult {
        val startTimeMillis = System.currentTimeMillis()
        try {
            waitTimeClientV3.waitTime(seconds)
        } catch (e : FeignException) {
            logger.info("status: " + e.status() + ", response: " + e.contentUTF8(), e)
            throw RuntimeException(e)
        }
        return ApiCallResult(
                isSucceed = true,
                isCircuitBreaker = false,
                processingTimeSeconds = (System.currentTimeMillis() - startTimeMillis).toFloat() / 1000.toFloat()
        )
    }

}
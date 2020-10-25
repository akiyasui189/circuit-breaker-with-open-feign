package io.spring.start.sample.circuitbreaker.services

import io.spring.start.sample.circuitbreaker.dtos.ApiCallResult
import io.spring.start.sample.circuitbreaker.repositories.SampleRepository
import org.springframework.stereotype.Service

@Service
class SampleService (val sampleRepository: SampleRepository) {

    fun getError() : ApiCallResult {
        return sampleRepository.getError()
    }

    fun getStubResponse() : ApiCallResult {
        return sampleRepository.getStubResponse()
    }

    /*
    fun okHttpClientWaitTime(seconds: Long) : ApiCallResult {
        return sampleRepository.okHttpClientWaitTime(seconds)
    }
     */

    fun randomFail() : ApiCallResult {
        return sampleRepository.randomFail()
    }

    fun randomFailWithFallback() : ApiCallResult {
        return sampleRepository.randomFailWithFallback()
    }

    fun succeedOrFail(isFail: Boolean) : ApiCallResult {
        return sampleRepository.succeedOrFail(isFail)
    }

    fun succeedOrFailWithFallback(isFail: Boolean) : ApiCallResult {
        return sampleRepository.succeedOrFailWithFallback(isFail)
    }

    fun waitTime(seconds: Long) : ApiCallResult {
        return sampleRepository.waitTime(seconds)
    }

    fun waitTimeZeroSeconds() : ApiCallResult {
        return sampleRepository.testReadTimeOutSettingCase0()
    }

    fun waitTimeOneSeconds() : ApiCallResult {
        return sampleRepository.testReadTimeOutSettingCase1()
    }

    fun waitTimeTwoSeconds() : ApiCallResult {
        return sampleRepository.testReadTimeOutSettingCase2()
    }

    fun waitTimeThreeSeconds() : ApiCallResult {
        return sampleRepository.testReadTimeOutSettingCase3()
    }

    fun waitTimeV2(seconds: Long) : ApiCallResult {
        return sampleRepository.waitTimeV2(seconds)
    }

    fun waitTimeV3(seconds: Long) : ApiCallResult {
        return sampleRepository.waitTimeV3(seconds)
    }

}
package io.spring.start.sample.circuitbreaker.controllers

import io.spring.start.sample.circuitbreaker.controllers.interfaces.StubBaseResponse
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("/stub")
class StubController (val logger: Logger) {

    @GetMapping("/wait-time")
    fun processingTime(@RequestParam("seconds") seconds: Long?) : ResponseEntity<StubBaseResponse> {
        val startTime = System.currentTimeMillis()
        val sec = seconds?: 0L
        // wait time
        try {
            logger.info("Request Parameter `seconds`: {}", sec)
            Thread.sleep(sec * 1000)
        } catch (e: InterruptedException) {
            logger.error("Occurred Error: " + e.message, e)
            return ResponseEntity(
                    StubBaseResponse(
                            "F",
                            "02001001",
                            "UNKNOWN_ERROR",
                            (System.currentTimeMillis() - startTime).toFloat() / 1000.toFloat()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
        return ResponseEntity(
                StubBaseResponse(
                        "S",
                        "00000000",
                        "OK",
                        (System.currentTimeMillis() - startTime).toFloat() / 1000.toFloat()
                ),
                HttpStatus.OK
        )
    }

    @GetMapping("/succeed-or-fail")
    fun succeedOrFail(@RequestParam("isFail") isFail: Boolean?) : ResponseEntity<StubBaseResponse> {
        val startTime = System.currentTimeMillis()
        val fail: Boolean = isFail?: false
        if (fail) {
            return ResponseEntity(
                    StubBaseResponse(
                            "F",
                            "02002001",
                            "FAILURE",
                            (System.currentTimeMillis() - startTime).toFloat() / 1000.toFloat()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
        return ResponseEntity(
                StubBaseResponse(
                        "S",
                        "00000000",
                        "OK",
                        (System.currentTimeMillis() - startTime).toFloat() / 1000.toFloat()
                ),
                HttpStatus.OK
        )
    }

    @GetMapping("/always-fail")
    fun alwaysFail() : ResponseEntity<StubBaseResponse> {
        val startTime = System.currentTimeMillis()
        return ResponseEntity(
                StubBaseResponse(
                        "F",
                        "02003001",
                        "FAILURE",
                        (System.currentTimeMillis() - startTime).toFloat() / 1000.toFloat()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @GetMapping("/random-fail")
    fun randomFail() : ResponseEntity<StubBaseResponse> {
        val startTime = System.currentTimeMillis()
        if ((Random.nextInt(1, 100) % 2) == 0) {
            return ResponseEntity(
                    StubBaseResponse(
                            "F",
                            "02004001",
                            "FAILURE",
                            (System.currentTimeMillis() + 1 - startTime).toFloat() / 1000.toFloat()
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
        return ResponseEntity(
                StubBaseResponse(
                        "S",
                        "00000000",
                        "OK",
                        (System.currentTimeMillis() + 1 - startTime).toFloat() / 1000.toFloat()
                ),
                HttpStatus.OK
        )
    }

}
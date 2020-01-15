package com.example.contractconsumer

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties
import org.springframework.http.RequestEntity
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate
import java.net.URI

@RunWith(SpringRunner::class)
@AutoConfigureWebClient(registerRestTemplate = true)
@AutoConfigureStubRunner(
        stubsMode = StubRunnerProperties.StubsMode.REMOTE,
        failOnNoStubs = true,
        ids = ["com.example:cars-api-producer-git:+:stubs:8090"],
        repositoryRoot = "git://https://github.com/cassiemichelleroberts/contract-producer.git",
        mappingsOutputFolder = "build/mappings"
)
class AuthFleetManagementClientTest {

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Test
    fun getAircraftForCurrentUser_whenTheFirstAmuIsLuke_returnsLukeAircraft_whenStatusIs200() {
        val endpoint = "http://localhost:8090/api/v1/car/b55dc50f-600f-4cbc-9b49-fed186cf6eb2"

        val request = RequestEntity.get(URI.create(endpoint))
                .build()

        val response = restTemplate.exchange(request, Car::class.java)

        assertThat(response.body?.name).isEqualTo("Tesla")
    }
}


data class Car(val name: String)

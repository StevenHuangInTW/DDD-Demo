package com.thoughtworks.ddd.sample.jingxi.representation.warehouseentry

import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class WarehouseEntryControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    def "we can create a warehouse entry"() {
        given: "a warehouse entry request"

        when: "we perform this warehouse entry request"
          def result = mvc.perform(MockMvcRequestBuilders.post("/warehouse-entries")
                  .contentType(MediaType.APPLICATION_JSON_UTF8)
                  .content("{}"))

        then: "we will get a warehouse entry"
          result.andExpect(status().isOk())

    }

}

package com.thoughtworks.ddd.sample.jingxi.representation.inboundorder

import com.thoughtworks.ddd.sample.jingxi.application.inboundorder.InboundOrderApplicationService
import com.thoughtworks.ddd.sample.jingxi.application.inboundorder.command.InboundOrderCreateCommand
import com.thoughtworks.ddd.sample.jingxi.domain.common.auditing.AuditingInfo
import com.thoughtworks.ddd.sample.jingxi.domain.inboundorder.model.InboundOrder
import com.thoughtworks.ddd.sample.jingxi.domain.inboundorder.model.InboundOrderItem
import com.thoughtworks.ddd.sample.jingxi.domain.inboundorder.model.InboundType
import com.thoughtworks.ddd.sample.jingxi.domain.inboundorder.model.ShipmentInfo
import com.thoughtworks.ddd.sample.jingxi.domain.inboundorder.model.Supplier
import com.thoughtworks.ddd.sample.jingxi.domain.inboundorder.model.Warehouse
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Ignore
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@WebMvcTest(InboundOrderController.class)
class InboundOrderControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    @MockBean
    InboundOrderApplicationService applicationService

    @Ignore
    def "we can create a warehouse entry"() {
        given: "a warehouse entry request"

        and: ""
          applicationService.create(_ as InboundOrderCreateCommand) >> prepareACreatedInboundOrder()

        when: "we perform this warehouse entry request"
          String payload = "{\n" +
                  "  \"type\": \"PURCHASE\",\n" +
                  "  \"targetWarehouse\": \"WMS56Y932K\",\n" +
                  "  \"items\": [\n" +
                  "    {\n" +
                  "      \"skuCode\": \"V4C5D6E2\",\n" +
                  "      \"quantity\": 100\n" +
                  "    },\n" +
                  "    {\n" +
                  "      \"skuCode\": \"V4C5D6E1\",\n" +
                  "      \"quantity\": 50\n" +
                  "    }\n" +
                  "  ],\n" +
                  "  \"shipmentInfo\": {\n" +
                  "    \"sendDate\": \"2019-05-19\",\n" +
                  "    \"transactionNo\": \"TRX-SXX2019051805XDJ34D8L9\"\n" +
                  "  }\n" +
                  "}"
          def result = mvc.perform(MockMvcRequestBuilders.post("/inbound-orders")
                  .contentType(MediaType.APPLICATION_JSON_UTF8)
                  .content(payload))

        then: "we will get a warehouse entry"
          result.andExpect(status().isOk())
                  .andExpect(jsonPath('$.id', Matchers.notNullValue()))

    }

    private static final InboundOrder prepareACreatedInboundOrder() {
        InboundOrder order = new InboundOrder(
                new Warehouse("西安市环普地下车库仓", "M09J9S001214D0",),
                [new InboundOrderItem(Math.random().longValue(), "XJM-YOUPO-001", 1L)],
                Supplier.builder().name("彩虹小吃城").code("V01D1892M900").build(),
                new ShipmentInfo("X1D19918734812", LocalDate.now()),
                InboundType.PURCHASE,
                new AuditingInfo("DING Jie", LocalDateTime.now())
        )

        return order
    }

}

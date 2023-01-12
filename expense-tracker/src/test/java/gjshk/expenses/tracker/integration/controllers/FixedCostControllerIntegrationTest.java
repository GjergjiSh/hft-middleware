package gjshk.expenses.tracker.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gjshk.expenses.tracker.TestObjectBuilder;
import gjshk.expenses.tracker.controllers.FixedCostController;
import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.services.FixedCostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FixedCostController.class)
class FixedCostControllerIntegrationTest {

    @Autowired
    private MockMvc mockedMvc;
    @MockBean
    private FixedCostService mockedFixedCostService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Integration test for getFixedCosts")
    //@TODO Is there a way to not hard code the test values?
    void getFixedCostsTest() throws Exception {
        List<FixedCost> dummyFixedCosts = TestObjectBuilder.listOfDummyFixedCosts();
        given(mockedFixedCostService.getFixedCosts())
                .willReturn(dummyFixedCosts);

        mockedMvc.perform(get("/fixedcosts"))
                .andDo(print())
                .andExpectAll(
                    status().isOk(),
                    jsonPath("$[0]", notNullValue()),
                    jsonPath("$[0].id", is(1L), Long.class),
                    jsonPath("$[0].title", is("Rent"), String.class),
                    jsonPath("$[0].value", is(700.0F), Float.class),
                    jsonPath("$[1]", notNullValue()),
                    jsonPath("$[1].id", is(2L), Long.class),
                    jsonPath("$[1].title", is("Insurance"), String.class),
                    jsonPath("$[1].value", is(500.0F), Float.class)
                );
    }

    @Test
    @DisplayName("Integration test for addNewFixedCost")
    void addNewFixedCostTest() throws Exception {
        FixedCost dummyFixedCost = TestObjectBuilder.dummyFixedCost();

        given(mockedFixedCostService.addNewFixedCost(any(FixedCost.class)))
                .willReturn(dummyFixedCost);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/fixedcosts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dummyFixedCost));

        mockedMvc.perform(mockRequest)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", notNullValue()),
                        jsonPath("$.id", is(1L), Long.class),
                        jsonPath("$.title", is("Rent"), String.class),
                        jsonPath("$.value", is(700.0F), Float.class)
                );

    }

    @Test
    @DisplayName("Integration test for setFixedCostValue")
    void setFixedCostValueTest() throws Exception {
        FixedCost dummyFixedCost = TestObjectBuilder.dummyFixedCost();

        given(mockedFixedCostService.setFixedCostValue(
                dummyFixedCost.getId(), 200))
                .willAnswer(invocationOnMock -> {
                    dummyFixedCost.setValue(200);
                    return dummyFixedCost;
                });


        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", dummyFixedCost.getId().toString());
        requestParams.add("newValue", "200");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/fixedcosts")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .params(requestParams);

        mockedMvc.perform(mockRequest)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", notNullValue()),
                        jsonPath("$.id", is(1L), Long.class),
                        jsonPath("$.title", is("Rent"), String.class),
                        jsonPath("$.value", is(200.0F), Float.class)
                );
    }
}
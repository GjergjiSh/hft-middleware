package gjshk.expenses.tracker.integration.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import gjshk.expenses.tracker.TestObjectBuilder;
import gjshk.expenses.tracker.entities.FixedCost;
import gjshk.expenses.tracker.entities.Plan;
import gjshk.expenses.tracker.services.FixedCostService;
import gjshk.expenses.tracker.services.PlanService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class PlanControllerIntegrationTest {

    @Autowired
    private MockMvc mockedMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FixedCostService mockedFixedCostService;
    @MockBean
    private PlanService mockedPlanService;

    @Test
    @DisplayName("Integration test for getPlans")
    void getPlansTest() throws Exception {
        List<Plan> dummyPlans = TestObjectBuilder.listOfDummyPlans();
        given(mockedPlanService.getPlans())
                .willReturn(dummyPlans);

        mockedMvc.perform(get("/plans"))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$[0]", notNullValue()),
                        jsonPath("$[0].id", is(1L), Long.class),
                        jsonPath("$[0].title", is("Test Plan"), String.class),
                        jsonPath("$[0].salary", is(2000.0F), Float.class),
                        jsonPath("$[0].desiredSavings", is(1000.0F), Float.class),
                        jsonPath("$[0].currentBalance", is(0.0F), Float.class),
                        jsonPath("$[0].dailyAllowance", is(0.0F), Float.class),
                        jsonPath("$[0].createdOn", is(TestObjectBuilder.testDate.toString())),
                        jsonPath("$[1]", notNullValue()),
                        jsonPath("$[1].id", is(2L), Long.class),
                        jsonPath("$[1].title", is("Test Plan 2"), String.class),
                        jsonPath("$[1].salary", is(2000.0F), Float.class),
                        jsonPath("$[1].desiredSavings", is(1000.0F), Float.class),
                        jsonPath("$[1].currentBalance", is(0.0F), Float.class),
                        jsonPath("$[1].dailyAllowance", is(0.0F), Float.class),
                        jsonPath("$[1].createdOn", is(TestObjectBuilder.testDate.toString()))
                );
    }

    @Test
    @DisplayName("Integration test for getPlanByTitle")
    void getPlanByTitleTest() throws Exception {
        Plan plan = TestObjectBuilder.dummyPlan();

        given(mockedPlanService.getPlanByTitle(plan.getTitle()))
                .willReturn(plan);

        String planUrl = String.format("/plans/title/%s", plan.getTitle());
        mockedMvc.perform(get(planUrl))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", notNullValue()),
                        jsonPath("$.id", is(plan.getId()), Long.class),
                        jsonPath("$.title", is(plan.getTitle()), String.class),
                        jsonPath("$.salary", is(plan.getSalary()), Float.class),
                        jsonPath("$.desiredSavings", is(plan.getDesiredSavings()), Float.class),
                        jsonPath("$.currentBalance", is(plan.getCurrentBalance()), Float.class),
                        jsonPath("$.dailyAllowance", is(plan.getDailyAllowance()), Float.class),
                        jsonPath("$.createdOn", is(plan.getCreatedOn().toString()))
                        /*@TODO
                        jsonPath("$.fixedCosts", is(plan.getFixedCosts()))*/
                );
    }

    @Test
    @DisplayName("Integration test for getPlanByCreatedOn")
    void getPlanByCreatedOnTest() throws Exception {
        Plan dummyPlan = TestObjectBuilder.dummyPlan();

        given(mockedPlanService.getPlanByCreatedOn(dummyPlan.getCreatedOn()))
                .willReturn(dummyPlan);

        String planUrl = String.format("/plans/createdOn/%s", dummyPlan.getCreatedOn());
        mockedMvc.perform(get(planUrl))
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", notNullValue()),
                        jsonPath("$.id", is(dummyPlan.getId()), Long.class),
                        jsonPath("$.title", is(dummyPlan.getTitle()), String.class),
                        jsonPath("$.salary", is(dummyPlan.getSalary()), Float.class),
                        jsonPath("$.desiredSavings", is(dummyPlan.getDesiredSavings()), Float.class),
                        jsonPath("$.currentBalance", is(dummyPlan.getCurrentBalance()), Float.class),
                        jsonPath("$.dailyAllowance", is(dummyPlan.getDailyAllowance()), Float.class),
                        jsonPath("$.createdOn", is(dummyPlan.getCreatedOn().toString()))
                        /*@TODO
                        jsonPath("$.fixedCosts", is(plan.getFixedCosts()))*/
                );
    }

    @Test
    @DisplayName("Integration test for addNewPlan")
    void addNewPlanTest() throws Exception {
        Plan dummyPlan = TestObjectBuilder.dummyPlan();

        given(mockedPlanService.addNewPlan(any(Plan.class)))
                .willReturn(dummyPlan);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/plans")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dummyPlan));

        mockedMvc.perform(mockRequest)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id", notNullValue()),
                        jsonPath("$.id", is(dummyPlan.getId()), Long.class),
                        jsonPath("$.title", is(dummyPlan.getTitle()), String.class),
                        jsonPath("$.salary", is(dummyPlan.getSalary()), Float.class),
                        jsonPath("$.desiredSavings", is(dummyPlan.getDesiredSavings()), Float.class),
                        jsonPath("$.currentBalance", is(dummyPlan.getCurrentBalance()), Float.class),
                        jsonPath("$.dailyAllowance", is(dummyPlan.getDailyAllowance()), Float.class),
                        jsonPath("$.createdOn", is(dummyPlan.getCreatedOn().toString()))
                        /*@TODO
                        jsonPath("$.fixedCosts", is(plan.getFixedCosts()))*/
                );
    }

    @Test
    @DisplayName("Integration test for addFixedCostToPlan")
    void addFixedCostToPlanTest() throws Exception {
        FixedCost dummyFixedCost = TestObjectBuilder.dummyFixedCost();
        Plan dummyPlan = TestObjectBuilder.dummyPlan();

        given(mockedPlanService.getPlanById(dummyPlan.getId()))
                .willReturn(dummyPlan);

        given(mockedFixedCostService.getFixedCostById(dummyFixedCost.getId()))
                .willReturn(dummyFixedCost);

        given(mockedPlanService.assignFixedCost(dummyPlan, dummyFixedCost))
                .willAnswer(invocationOnMock -> {
                    dummyPlan.addFixedCost(dummyFixedCost);
                    return dummyPlan;
                });

        String planUrl = String.format(
                "/plans/%d/fixedcosts/%s",
                dummyPlan.getId(),
                dummyFixedCost.getId()
        );

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post(planUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(planUrl);

        mockedMvc.perform(mockRequest)
                .andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$", notNullValue()),
                        jsonPath("$.id", is(dummyPlan.getId()), Long.class),
                        jsonPath("$.title", is(dummyPlan.getTitle()), String.class),
                        jsonPath("$.salary", is(dummyPlan.getSalary()), Float.class),
                        jsonPath("$.desiredSavings", is(dummyPlan.getDesiredSavings()), Float.class),
                        jsonPath("$.currentBalance", is(dummyPlan.getCurrentBalance()), Float.class),
                        jsonPath("$.dailyAllowance", is(dummyPlan.getDailyAllowance()), Float.class),
                        jsonPath("$.createdOn", is(dummyPlan.getCreatedOn().toString())),
                        jsonPath("$.fixedCosts[0].id", is(dummyFixedCost.getId()), Long.class),
                        jsonPath("$.fixedCosts[0].title", is(dummyFixedCost.getTitle()), String.class),
                        jsonPath("$.fixedCosts[0].value", is(dummyFixedCost.getValue()), Float.class)
                );

    }

}

package cat.udl.eps.softarch.demo.steps;

import static org.hamcrest.Matchers.is;

import cat.udl.eps.softarch.demo.domain.Transaction;
import cat.udl.eps.softarch.demo.repository.TransactionRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.json.JSONObject;
import org.junit.Assert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateTransDefs {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private StepDefs stepDefs;


    @When("^I Create a new Transaction with price ([\\d-.]+)$")
    public void iCreateANewTransactionWithPrice(BigDecimal price) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setPrice(price);

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(transaction))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @Then("The transaction status with id {long} is {string}")
    public void theTransactionStatusIs(long id, String status) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/transactions/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(status)));
    }

    @And("I change the status of the transaction with id {long} to {string}")
    public void iChangeTheStatusOfTheTransactionTo(long id, String status) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/transactions/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((new JSONObject().put("status", status)).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}

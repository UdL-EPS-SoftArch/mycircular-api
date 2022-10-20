package cat.udl.eps.softarch.demo.steps;

import static org.hamcrest.Matchers.is;

import cat.udl.eps.softarch.demo.domain.Transaction;
import cat.udl.eps.softarch.demo.repository.TransactionRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.json.JSONObject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CreateTransDefs {

    String newResourceUri;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
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
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }


    @Then("The transaction status is {string}")
    public void theTransactionStatusIs(String status) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get(newResourceUri)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.status", is(status)));
    }

    @And("I change the status of the transaction to {string}")
    public void iChangeTheStatusOfTheTransactionTo(String status) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        patch(newResourceUri)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((new JSONObject().put("status", status)).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Given("I Create a new Transaction with price {string}, the buyer is {string} and the seller is {string}")
    public void iCreateANewTransactionWithPriceAndTheBuyerIs(String price, String buyer, String seller) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setPrice(new BigDecimal(price));
        transaction.setBuyer(userRepository.findByUsernameContaining(buyer).get(0));
        transaction.setSeller(userRepository.findByUsernameContaining(seller).get(0));
        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new JSONObject(
                                        stepDefs.mapper.writeValueAsString(transaction)
                                ).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
        newResourceUri = stepDefs.result.andReturn().getResponse().getHeader("Location");
    }
}

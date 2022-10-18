package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Transaction;
import cat.udl.eps.softarch.demo.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class CreateTransDefs {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private StepDefs stepDefs;


    @And("There is no transaction created")
    public void thereIsNoTransactionCreated() {
        Assert.assertEquals(0, transactionRepository.count());
    }

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

    @Then("I put the Transaction price {string}")
    public void iModifyTheTransactionPrice(String price) throws Exception {
        Transaction transaction = transactionRepository.findById(1L).get();
        BigDecimal priceDecimal = new BigDecimal(price);
        transaction.setPrice(priceDecimal);
        transactionRepository.save(transaction);
    }
    @Then("There is a transaction created")
    public void thereIsATransactionCreated() {
        Assert.assertEquals(1, transactionRepository.count());
    }
    @And("Now the Transaction price is {string}")
    public void iVerifyThePrice(String price) throws Exception {
        Transaction transaction = transactionRepository.findById(1L).get();
        BigDecimal priceDecimal = new BigDecimal(price);
        Assert.assertEquals(transaction.getPrice(),priceDecimal);
    }
    @Then("The transaction status is {string}")
    public void theTransactionStatusIs(String status) {
        Transaction transaction = transactionRepository.findById(1L).get();
        Assert.assertEquals(Transaction.StatusTypes.valueOf(status), transaction.getStatus());

    }

    @And("I change the status of the transaction with id {long} to {string}")
    public void iChangeTheStatusOfTheTransactionTo(long id,String status) throws Exception {
        JSONObject newStatus = new JSONObject();
        newStatus.put("status",Transaction.StatusTypes.valueOf(status));

        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/transactions/{id}",id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((new JSONObject().put("status",status)).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
}

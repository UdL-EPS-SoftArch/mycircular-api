package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Transaction;
import cat.udl.eps.softarch.demo.repository.TransactionRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.junit.Assert;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @When("I Create a new Transaction")
    public void iCreateANewTransaction() throws Exception {
        Transaction transaction = new Transaction();

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/transactions")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(transaction))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Then("There is a transaction created")
    public void thereIsATransactionCreated() {
        Assert.assertEquals(1, transactionRepository.count());
    }

    @Then("The transaction status is {string}")
    public void theTransactionStatusIs(String status) {
        Transaction transaction = transactionRepository.findById(1L).get();
        Assert.assertEquals(Transaction.StatusTypes.valueOf(status), transaction.getStatus());

    }
}

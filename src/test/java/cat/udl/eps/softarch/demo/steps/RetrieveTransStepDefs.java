package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Transaction;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.AnnouncementRepository;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveTransStepDefs {

    String newResourceUri;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository userRepository;


    @And("There is a transaction created with id {int} Buyer {string} and Seller {string} and announcement {int}")
    public void thereIsATransactionCreatedWithIdIntBuyerStringAndSellerString(int id, String buyer, String seller, int announcementId) throws Exception{
        Long lid = (long) id;
        if (!transactionRepository.existsById(lid)) {
            Transaction transaction = new Transaction();
            transaction.setId(lid);

            List<User> userbuyer = userRepository.findByUsernameContaining(buyer);
            if (userbuyer.size() != 0)
                transaction.setBuyer(userbuyer.get(0));

            List<User> userSeller = userRepository.findByUsernameContaining(seller);
            if (userbuyer.size() != 0)
                transaction.setSeller(userSeller.get(0));

            BigDecimal price = new BigDecimal("20.05");
            transaction.setPrice(price);
            ZonedDateTime now = ZonedDateTime.now();
            transaction.setCreationDate(now);
            if (transaction.getStatus() == null) {
                transaction.setStatus(Transaction.StatusTypes.INITIALIZED);
            }
            transaction.setAnnouncementAbout(announcementRepository.findById(announcementId).get(0));
            transactionRepository.save(transaction);
        }
    }
    @When("I list the transactions of all users")
    public void IlistTheTransactionsOfAllUsers() throws Exception{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/transactions")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
    @When("I list the transactions with buyer {string}")
    public void IlistTheTransactionsWithUser(String user) throws Exception{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/transactions/search/findByBuyer_Username?username={username}", user)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

    }
    @When("I list the transactions with id {int}")
    public void IlistTheTransactionsWithId(int id) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/transactions/" + id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("The number of transactions is {int}")
    public void theNumberOfTransactionsIs(int numTrans) throws Exception {
        stepDefs.result.andExpect(jsonPath("$._embedded.transactions", hasSize(numTrans)));
    }
}

package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Transaction;
import cat.udl.eps.softarch.demo.domain.User;
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
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveTransStepDefs {

    String newResourceUri;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository userRepository;

    @When("I list the transactions for an User {String}")
    public void iListTheTranascitonsForAnUser(String user) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/reviews/{about}", user)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("There is a transaction created with id {int} Buyer {string} and Seller {String}")
    public void thereIsATransactionCreatedWithIdIntBuyerStringAndSellerString(int id, String buyer, String seller) {
        Long lid = (long) id;
        if (!transactionRepository.existsById(lid)) {
            Transaction transaction = new Transaction();

            List<User> userbuyer = userRepository.findByUsernameContaining(buyer);
            if (userbuyer.size() != 0)
                transaction.setBuyer(userbuyer.get(0));

            List<User> userSeller = userRepository.findByUsernameContaining(seller);
            if (userbuyer.size() != 0)
                transaction.setSeller(userSeller.get(0));
        }
    }

    @And("There is a transaction created with Buyer {string} and Seller {String}")
    public void thereIsATransactionCreatedWithBuyerStringAndSellerString(String user1,String user2) {
    }
}

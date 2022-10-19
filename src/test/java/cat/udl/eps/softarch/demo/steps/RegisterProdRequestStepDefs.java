package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.ProdRequest;

import cat.udl.eps.softarch.demo.domain.User;

import cat.udl.eps.softarch.demo.repository.ProdRequestRepository;

import cat.udl.eps.softarch.demo.repository.UserRepository;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterProdRequestStepDefs {

    @Autowired
    private ProdRequestRepository prodRequestRepository;
    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private UserRepository userRepository;

    @When("I Create a new product request")
    public void iCreateANewProductRequest() throws Exception {
        ProdRequest prodRequest = new ProdRequest();

        prodRequest.setName("croqueta");
        prodRequest.setPrice(new BigDecimal(50));
        prodRequest.setDescription("las croquestas mas ricas de la mama");

        User nene = new User();
        nene.setEmail("nene@cocina.casa");
        nene.setUsername("nene");
        nene.setPassword("password");

        prodRequest.setRequester(nene);

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/prodrequests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(prodRequest))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Then("There are {int} product request created")
    public void thereAreProductRequestCreated(int prodRequestsNum) {
        Assert.assertEquals(prodRequestsNum, prodRequestRepository.count());
    }

    @And("There is a product request created with name {string}, price {int}, description {string} by {string}")
    public void thereIsAProductRequestCreatedWithNamePriceDescriptionBy(String name, int price, String description, String requesterName) throws Exception {
        ProdRequest prodRequest = new ProdRequest();
        prodRequest.setName(name);
        prodRequest.setPrice(new BigDecimal(price));
        prodRequest.setDescription(description);


        Optional<User> users = userRepository.findById(requesterName);
        User requester;

        if (users.isPresent()) {
            requester = users.get();
        } else {
            requester = new User();
            requester.setUsername(requesterName);
            requester.setPassword("password");
            requester.setEmail(requesterName + "@gmail.com");
        }

        prodRequest.setRequester(requester);

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/prodRequests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(prodRequest))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @When("I Create a new product request with name {string}, price {int}, description {string}")
    public void iCreateANewProductRequestWithNamePriceDescription(String name, int price, String description) throws Exception {
        ProdRequest prodRequest = setProdRequestParams(name, price, description);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/requests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(prodRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    private ProdRequest setProdRequestParams(String name, int price, String description) {
        ProdRequest prodRequest = new ProdRequest();
        prodRequest.setName(name);
        prodRequest.setPrice(new BigDecimal(price));
        prodRequest.setDescription(description);

        String currentUsername = getCurrentUsername();
        User requester = getCurrentUser(currentUsername);
        prodRequest.setRequester(requester);

        return prodRequest;
    }

    private String getCurrentUsername() {
        return AuthenticationStepDefs.currentUsername;
    }

    private User getCurrentUser(String username) {
        Optional<User> users = userRepository.findById(username);
        return users.orElseGet(User::new);
    }
}

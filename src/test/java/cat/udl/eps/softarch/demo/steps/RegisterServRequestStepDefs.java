package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.ServRequest;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.*;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterServRequestStepDefs {

    @Autowired
    private ServRequestRepository servRequestRepository;
    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private UserRepository userRepository;

    @When("I Create a new service request")
    public void iCreateANewServiceRequest() throws Exception {
        ServRequest servRequest = new ServRequest();

        servRequest.setName("croqueta");
        servRequest.setPrice(new BigDecimal(50));
        servRequest.setDescription("las croquestas mas ricas de la mama");

        User nene = new User();
        nene.setEmail("nene@cocina.casa");
        nene.setUsername("nene");
        nene.setPassword("password");

        servRequest.setRequester(nene);

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/servRequests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(servRequest))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @Then("There are {int} service request created")
    public void thereAreServiceRequestCreated(int servRequestsNum) {
        Assert.assertEquals(servRequestsNum, servRequestRepository.count());
    }


    @And("There is a service request created with name {string}, price {int}, description {string} by {string}")
    public void thereIsAServiceRequestCreatedWithNamePriceDescriptionBy(String name, int price, String description, String requesterName) throws Exception{
        ServRequest servRequest = new ServRequest();
        servRequest.setName(name);
        servRequest.setPrice(new BigDecimal(price));
        servRequest.setDescription(description);


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

        servRequest.setRequester(requester);

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/servRequests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(servRequest))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print());
    }


    @When("I Create a new service request with name {string}, price {int}, description {string}")
    public void iCreateANewServiceRequestWithNamePriceDescription(String name, int price, String description) throws Exception {
        ServRequest servRequest = setProdRequestParams(name, price, description);

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/servRequests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(servRequest))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    private ServRequest setProdRequestParams(String name, int price, String description) {
        ServRequest servRequest = new ServRequest();
        servRequest.setName(name);
        servRequest.setPrice(new BigDecimal(price));
        servRequest.setDescription(description);

        String currentUsername = getCurrentUsername();
        User requester = getCurrentUser(currentUsername);
        servRequest.setRequester(requester);

        return servRequest;
    }

    private String getCurrentUsername() {
        return AuthenticationStepDefs.currentUsername;
    }

    private User getCurrentUser(String username) {
        Optional<User> users = userRepository.findById(username);
        return users.orElseGet(User::new);
    }
}

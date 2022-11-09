package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.NotFoundException;
import cat.udl.eps.softarch.demo.repository.ProdRequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RetrieveProdRequestStepDefs {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProdRequestRepository prodRequestRepository;

    @Autowired
    private StepDefs stepDefs;

    @When("I retrieve my own created product requests")
    public void iRetrieveMyOwnCreatedProductRequests() throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/prodRequests", getCurrentUsername())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
                        .queryParam("username", getCurrentUsername())

        ).andDo(print())
        ;
    }

    private User getCurrentUser() {
        String username = getCurrentUsername();
        Optional<User> users = userRepository.findById(username);
        if (users.isPresent()) {
            return users.get();
        }
        throw new NotFoundException();
    }

    private String getCurrentUsername() {
        return AuthenticationStepDefs.currentUsername;
    }

    private User getOtherUser(String username) {
        Optional<User> users = userRepository.findById(username);
        if (users.isPresent()) {
            return users.get();
        }
        throw new NotFoundException();
    }

    @When("I retrieve product requests from user {string}")
    public void iRetrieveProductRequestsFromUser(String otherUsername) throws Exception {

        stepDefs.result = stepDefs.mockMvc.perform(

                get("/prodRequests", otherUsername)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
                        .queryParam("username", otherUsername)

        ).andDo(print())
        ;
    }

    @And("I see {int} product request(s) from {string}")
    public void iSeeProductRequestsFrom(int numRequests, String username) throws Exception {

        ResultActions userPetitionResult = stepDefs.result;

        String currentUsername = getCurrentUsername();
        User user;

        if (currentUsername.equals(username)) {
            user = getCurrentUser();
        } else {
            user = getOtherUser(username);
        }

        userPetitionResult.andDo(print());
        //  System.out.println(jsonPath("$[0]").);
        userPetitionResult.andExpect(jsonPath("$[0].requester").value("/users/" + user.getUsername()));

        List<Request> userRequests = prodRequestRepository.findByRequester(user);
        Assertions.assertEquals(userRequests.size(), numRequests);
        stepDefs.result.andExpect(jsonPath("$", hasSize(numRequests)))
        ;
    }

    @And("I can't see any product request")
    public void iCanTSeeAnyProductRequest() throws Exception {

        stepDefs.result.andExpect(jsonPath("$").isEmpty());

    }
}

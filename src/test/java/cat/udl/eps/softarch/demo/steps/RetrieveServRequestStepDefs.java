package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.NotFoundException;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import cat.udl.eps.softarch.demo.repository.ServRequestRepository;
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


public class RetrieveServRequestStepDefs {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ServRequestRepository servRequestRepository;

    @Autowired
    private StepDefs stepDefs;

    @When("I retrieve my own created service requests")
    public void iRetrieveMyOwnCreatedServiceRequests() throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                get("/servRequests", getCurrentUsername())
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

    @And("I see {int} service requests from {string}")
    public void iSeeServiceRequestsFrom(int numRequests, String username) throws Exception {

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

        List<Request> userRequests = servRequestRepository.findByRequester(user);
        Assertions.assertEquals(userRequests.size(), numRequests);
        stepDefs.result.andExpect(jsonPath("$", hasSize(numRequests)))
        ;

    }

    @And("I can't see any service request")
    public void iCanTSeeAnyServiceRequest() throws Exception {
        stepDefs.result.andExpect(jsonPath("$").doesNotExist());
    }

    @When("I retrieve service requests from user {string}")
    public void iRetrieveServiceRequestsFromUser(String otherUsername) throws Exception {

        stepDefs.result = stepDefs.mockMvc.perform(

                get("/servRequests", otherUsername)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
                        .queryParam("username", otherUsername)

        ).andDo(print())
        ;

    }
}

package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.NotFoundException;
import cat.udl.eps.softarch.demo.exception.UnauthorizedException;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RetrieveRequestStepDefs {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private StepDefs stepDefs;



    @When("I retrieve my own created requests")
    public void iRetrieveMyOwnCreatedRequests() throws Exception {

        //TODO: para diferenciar entre algo que ya esta creado de cuando el usuario lo crea, puedo poner un post con un .post("/requests").with(anonymous())
        stepDefs.result = stepDefs.mockMvc.perform(

                        get("/requests", getCurrentUsername())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                                .queryParam("username", getCurrentUsername())

                ).andDo(print())
        ;

    }

    private User getCurrentUser() {
        String username = getCurrentUsername();
        Optional<User> users = userRepository.findById(username);
        return users.orElseGet(User::new);
    }

    private String getCurrentUsername() {
        return AuthenticationStepDefs.currentUsername;
    }

    private User getOtherUser(String username) {
        Optional<User> users = userRepository.findById(username);
        if(users.isPresent()) {
            return users.get();
        }
        throw new NotFoundException();
    }

    @And("I see {int} requests from {string}")
    public void iSeeRequestsFrom(int numRequests, String username) throws Exception {

        ResultActions userPetitionResult = stepDefs.result;

        String currentUsername = getCurrentUsername();
        User user;

        if(currentUsername.equals(username)) {
            user = getCurrentUser();
        } else {
            user = getOtherUser(username);
        }

        userPetitionResult.andExpect(jsonPath("$[0].requester").value("/users/" + user.getUsername()));

        List<Request> userRequests = requestRepository.findByRequester(user);
        Assertions.assertEquals(userRequests.size(), numRequests);
        stepDefs.result.andExpect(jsonPath("$", hasSize(numRequests)))
        ;
    }

    @When("I retrieve requests from user {string}")
    public void iRetrieveRequestsFromUser(String otherUsername) throws Exception {

        stepDefs.result = stepDefs.mockMvc.perform(

                get("/requests", otherUsername)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
                        .queryParam("username", otherUsername)

        ).andDo(print())
        ;

    }

    @And("I can't see any request")
    public void iCanTSeeAnyRequest() throws Exception {
        stepDefs.result.andExpect(jsonPath("$").doesNotExist());
    }


}

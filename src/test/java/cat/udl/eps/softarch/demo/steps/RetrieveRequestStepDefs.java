package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
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
//                .andExpect(status().isOk())
  //              .andExpect(jsonPath("$[0].requester").value("/users/" + getCurrentUsername()))
        ;

//        if(status().is2xxSuccessful()) {
//            stepDefs.
//        }

    }

    private User getCurrentUser() {
        String username = getCurrentUsername();
        Optional<User> users = userRepository.findById(username);
        return users.orElseGet(User::new);
    }

    private String getCurrentUsername() {
        return AuthenticationStepDefs.currentUsername;
    }

    @And("I see {int} requests")
    public void iSeeRequests(int numRequests) throws Exception {
        User currentUser = getCurrentUser();

        stepDefs.result.andExpect(jsonPath("$[0].requester").value("/users/" + getCurrentUsername()));

        List<Request> userRequests = requestRepository.findByRequester(currentUser);
        Assertions.assertEquals(userRequests.size(), numRequests);
        stepDefs.result.andExpect(jsonPath("$", hasSize(numRequests)))
        ;
    }

//    @And("This is a fake step")
//    public void thisIsAFakeStep() throws Exception {
//
//        stepDefs.result = stepDefs.mockMvc.perform(
//
//                get("/requests")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .with(AuthenticationStepDefs.authenticate())
//                ).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].requester").value("/users/" + getCurrentUsername()))
//                ;
//
//    }

    @When("I retrieve requests from user {string}")
    public void iRetrieveRequestsFromUser(String otherUsername) throws Exception {

    }

    @And("I can't see any request")
    public void iCanTSeeAnyRequest() throws Exception {
        stepDefs.result.andDo(print());
        stepDefs.result.andExpect(jsonPath("$").doesNotExist());
    }


}

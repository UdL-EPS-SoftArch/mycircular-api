package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        User currentUser = getCurrentUser();
        List<Request> userRequests = requestRepository.findByRequester(currentUser);

        Assertions.assertEquals(userRequests.size(), 1);
//        List<Long> userRequestsId = new ArrayList<>();
//        for(Request request : userRequests) {
//            userRequestsId.add(request.getId());
//        }

        Long userRequestId = userRequests.get(0).getId();
        //TODO: si quisiera sacar las requests propias y hubieran varias, como las saco? hago el get dentro de un bucle?
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/requests/" + userRequestId)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print())
                .andExpect(status().isOk())

        /*
        en la de nfts, hacen:
            .andExpect(jsonPath("$._embedded.nFTs[0].uri", is(String)))
         */

                ;
                ;//.andExpect(jsonPath("$.requester", is()));
        String content = stepDefs.result.andReturn().getResponse().getContentAsString();
        System.out.println("CONTENT: " + content);
        System.out.println("PRUEBA: " + stepDefs.result.andReturn().getRequest().getAttribute("_links"));
        //ResultActions userRequests2 = stepDefs.result;
        //userRequests2.andReturn().getRequest().getContentAsString().
      //  System.out.println( "RESULTADOS: " + userRequests);
      //  System.out.println("RETURN: " + userRequests.andReturn());
       // System.out.println("GET REQUEST: " + userRequests.andReturn().getRequest());
        //System.out.println(userRequests2.andExpect(jsonPath("$._embedded.requests").exists()));

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
    public void iSeeRequests(int numRequests) {
        User currentUser = getCurrentUser();
        List<Request> userRequests = requestRepository.findByRequester(currentUser);
        Assertions.assertEquals(userRequests.size(), numRequests);
    }
}

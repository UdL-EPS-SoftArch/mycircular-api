package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.ServRequest;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.NotFoundException;
import cat.udl.eps.softarch.demo.repository.ServRequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class DeleteServRequestStepDefs {

    @Autowired
    private ServRequestRepository servRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StepDefs stepDefs;

    private Exception e;

    @And("There is a service request already created")
    public void thereIsAServiceRequestAlreadyCreated() {
        ServRequest servRequest = new ServRequest();
        servRequest.setName("croqueta2");
        servRequest.setPrice(new BigDecimal(100));
        servRequest.setDescription("le hago la competencia a la mama");
        User requester = new User();
        requester.setUsername("manolo");
        requester.setPassword("password");
        requester.setEmail("manolo" + "@gmail.com");
        servRequest.setRequester(requester);

        servRequest.setRequester(requester);
        servRequestRepository.save(servRequest);
        Assert.assertEquals(1, servRequestRepository.count());
    }

    @When("I delete a service request with id {string}")
    public void iDeleteAServiceRequestWithId(String id) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/servRequests/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }
    private String getCurrentUsername() {
        return AuthenticationStepDefs.currentUsername;
    }


    @When("I delete my own created service requests")
    public void iDeleteMyOwnCreatedServiceRequests() throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/servRequests", getCurrentUsername())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
                        .queryParam("username", getCurrentUsername())

        ).andDo(print())
        ;
    }

    private User getOtherUser(String username) {
        Optional<User> users = userRepository.findById(username);
        if (users.isPresent()) {
            return users.get();
        }
        throw new NotFoundException();
    }

    private User getCurrentUser() {
        String username = getCurrentUsername();
        Optional<User> users = userRepository.findById(username);
        if (users.isPresent()) {
            return users.get();
        }
        throw new NotFoundException();
    }

    @Then("I see {int} service request from {string}")
    public void iSeeServiceRequestFrom(int numRequests, String username) throws Exception {
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

    @When("I delete service requests from user {string}")
    public void iDeleteServiceRequestsFromUser(String username) throws Exception {
        try {
            stepDefs.result = stepDefs.mockMvc.perform(
                    delete("/servRequests", username)
                            .accept(MediaType.APPLICATION_JSON)
                            .with(AuthenticationStepDefs.authenticate())
                            .queryParam("username", username)

            ).andDo(print())
            ;
        } catch (NotFoundException Nf) {
            e = Nf;
        }
    }

    private User getUser(String username) {
        Optional<User> users = userRepository.findById(username);
        if (users.isPresent()) {
            return users.get();
        }
        throw new NotFoundException();
    }

    private Request getRequestByParams(String name, int price, String description, String username) {
        BigDecimal requestPrice = new BigDecimal(price);
        User requester = getUser(username);

        List<Request> requestList = servRequestRepository.findByNameAndPriceAndDescriptionAndRequester(name, requestPrice, description, requester);
        if (requestList.isEmpty()) {
            throw new NotFoundException();
        }
        return requestList.get(0);

    }

    @When("I delete a service request with name {string}, price {int}, description {string} by {string}")
    public void iDeleteAServiceRequestWithNamePriceDescriptionBy(String name, int price, String description, String username) throws Exception {
        try {
            Long requestId = getRequestByParams(name, price, description, username).getId();

            stepDefs.result = stepDefs.mockMvc.perform(
                            delete("/servRequests/" + requestId)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
        } catch (NotFoundException Nf) {
            e = Nf;
        }
    }

    @Then("The service request is not found")
    public void theServiceRequestIsNotFound() {
        Assertions.assertEquals(NotFoundException.class, e.getClass());
    }

    @And("I want to check that the service request with name {string}, price {int}, description {string} by {string} doesn't exist")
    public void iWantToCheckThatTheServiceRequestWithNamePriceDescriptionByDoesnTExist(String name, int price, String description, String username) {
        Assertions.assertThrows(NotFoundException.class, () -> getRequestByParams(name, price, description, username));
    }

    @And("I want to check that the service request still exist")
    public void iWantToCheckThatTheServiceRequestStillExist() {
        Assert.assertNotEquals(0, servRequestRepository.count());
    }
}

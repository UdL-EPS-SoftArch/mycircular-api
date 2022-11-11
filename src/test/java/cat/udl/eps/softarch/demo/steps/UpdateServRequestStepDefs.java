package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.NotFoundException;
import cat.udl.eps.softarch.demo.repository.ServRequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class UpdateServRequestStepDefs {
    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private ServRequestRepository servRequestRepository;
    @Autowired
    private UserRepository userRepository;
    private Exception e;
    @When("I modify my own created service requests with price {int}")
    public void iModifyMyOwnCreatedServiceRequestsWithPrice(int newPrice) throws Exception {
        String currentUser = getCurrentUsername();
        List<Request> myRequests = servRequestRepository.findByRequester(getUser(currentUser));
        Request modifiedRequest = myRequests.get(0);
        Long requestId = modifiedRequest.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/servRequests/{id}", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new JSONObject().put("price", new BigDecimal(newPrice))).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    private String getCurrentUsername() {
        return AuthenticationStepDefs.currentUsername;
    }

    private User getUser(String username) {
        Optional<User> users = userRepository.findById(username);
        if (users.isPresent()) {
            return users.get();
        }
        throw new NotFoundException();
    }

    @Then("The price of my service request I tried to change is not {int}")
    public void thePriceOfMyServiceRequestITriedToChangeIsNot(int newPrice) {
        String currentUser = getCurrentUsername();
        List<Request> myRequests = servRequestRepository.findByRequester(getUser(currentUser));
        BigDecimal unexpectedPrice = new BigDecimal(newPrice);
        Assert.assertNotEquals(unexpectedPrice, myRequests.get(0).getPrice());
    }

    @When("I modify {string}'s service requests with price {int}")
    public void iModifySServiceRequestsWithPrice(String othersUsername, int newPrice) throws Exception {
        List<Request> othersRequests = servRequestRepository.findByRequester(getUser(othersUsername));
        Request modifiedRequest = othersRequests.get(0);
        Long requestId = modifiedRequest.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                patch("/servRequests/{id}", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new JSONObject().put("price", new BigDecimal(newPrice))).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @Then("The price of {string}'s service request I tried to change is not {int}")
    public void thePriceOfSServiceRequestITriedToChangeIsNot(String othersUsername, int newPrice) {
        List<Request> othersRequests = servRequestRepository.findByRequester(getUser(othersUsername));
        BigDecimal unexpectedPrice = new BigDecimal(newPrice);
        Assert.assertNotEquals(unexpectedPrice, othersRequests.get(0).getPrice());
    }

    @When("I modify a service request with name {string}, price {int}, description {string} by {string} with new price {int}")
    public void iModifyAServiceRequestWithNamePriceDescriptionByWithNewPrice(String name, int price, String description, String username, int newPrice) {
        try {
            Long requestId = getRequestByParams(name, price, description, username).getId();
            stepDefs.result = stepDefs.mockMvc.perform(
                    patch("/servRequests/{id}", requestId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content((new JSONObject().put("price", new BigDecimal(newPrice))).toString())
                            .accept(MediaType.APPLICATION_JSON)
                            .with(AuthenticationStepDefs.authenticate())
            ).andDo(print());
        } catch (Exception Nf) {
            e = Nf;
        }
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

    @Then("The service request to modify is not found")
    public void theServiceRequestToModifyIsNotFound() {
        Assertions.assertEquals(NotFoundException.class, e.getClass());
    }

    @When("I modify my own created service requests with price {int} \\(put)")
    public void iModifyMyOwnCreatedServiceRequestsWithPricePut(int newPrice) throws Exception {
        String currentUser = getCurrentUsername();
        List<Request> myRequests = servRequestRepository.findByRequester(getUser(currentUser));
        Request modifiedRequest = myRequests.get(0);
        Long requestId = modifiedRequest.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/servRequests/{id}", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new JSONObject().put("price", new BigDecimal(newPrice))).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @When("I modify {string}'s service requests with price {int} \\(put)")
    public void iModifySServiceRequestsWithPricePut(String othersUsername, int newPrice) throws Exception {
        List<Request> othersRequests = servRequestRepository.findByRequester(getUser(othersUsername));
        Request modifiedRequest = othersRequests.get(0);
        Long requestId = modifiedRequest.getId();

        stepDefs.result = stepDefs.mockMvc.perform(
                put("/servRequests/{id}", requestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content((new JSONObject().put("price", new BigDecimal(newPrice))).toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
        ).andDo(print());
    }

    @When("I modify a service request with name {string}, price {int}, description {string} by {string} with new price {int} \\(put)")
    public void iModifyAServiceRequestWithNamePriceDescriptionByWithNewPricePut(String name, int price, String description, String username, int newPrice) {
        try {
            Long requestId = getRequestByParams(name, price, description, username).getId();
            stepDefs.result = stepDefs.mockMvc.perform(
                    put("/servRequests/{id}", requestId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content((new JSONObject().put("price", new BigDecimal(newPrice))).toString())
                            .accept(MediaType.APPLICATION_JSON)
                            .with(AuthenticationStepDefs.authenticate())
            ).andDo(print());
        } catch (Exception Nf) {
            e = Nf;
        }
    }
}

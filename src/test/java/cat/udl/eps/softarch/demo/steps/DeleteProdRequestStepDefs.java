package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.ProdRequest;
import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.NotFoundException;
import cat.udl.eps.softarch.demo.repository.ProdRequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class DeleteProdRequestStepDefs {

    @Autowired
    private ProdRequestRepository prodRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StepDefs stepDefs;

    private Exception e;

    @And("There is a product request already created")
    public void thereIsAProductRequestAlreadyCreated() {
        ProdRequest prodRequest = new ProdRequest();
        prodRequest.setName("croqueta2");
        prodRequest.setPrice(new BigDecimal(100));
        prodRequest.setDescription("le hago la competencia a la mama");
        User requester = new User();
        requester.setUsername("manolo");
        requester.setPassword("password");
        requester.setEmail("manolo" + "@gmail.com");
        prodRequest.setRequester(requester);

        prodRequest.setRequester(requester);
        prodRequestRepository.save(prodRequest);
        Assert.assertEquals(1, prodRequestRepository.count());
    }

    @When("I delete a product request with id {string}")
    public void iDeleteAProductRequestWithId(String id) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/prodRequests/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    private String getCurrentUsername() {
        return AuthenticationStepDefs.currentUsername;
    }

    @When("I delete my own created product requests")
    public void iDeleteMyOwnCreatedProductRequests() throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/prodRequests", getCurrentUsername())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
                        .queryParam("username", getCurrentUsername())

        ).andDo(print())
        ;
    }

    @When("I delete product requests from user {string}")
    public void iDeleteProductRequestsFromUser(String username) throws Exception {
        try {
            stepDefs.result = stepDefs.mockMvc.perform(
                    delete("/prodRequests", username)
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

        List<Request> requestList = prodRequestRepository.findByNameAndPriceAndDescriptionAndRequester(name, requestPrice, description, requester);
        if (requestList.isEmpty()) {
            throw new NotFoundException();
        }
        return requestList.get(0);

    }

    @When("I delete a product request with name {string}, price {int}, description {string} by {string}")
    public void iDeleteAProductRequestWithNamePriceDescriptionBy(String name, int price, String description, String username) throws Exception {

        try {
            Long requestId = getRequestByParams(name, price, description, username).getId();

            stepDefs.result = stepDefs.mockMvc.perform(
                            delete("/prodRequests/" + requestId)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
        } catch (NotFoundException Nf) {
            e = Nf;
        }
    }

    @And("I want to check that the product request with name {string}, price {int}, description {string} by {string} doesn't exist")
    public void iWantToCheckThatTheProductRequestWithNamePriceDescriptionByDoesnTExist(String name, int price, String description, String username) {
        Assertions.assertThrows(NotFoundException.class, () -> getRequestByParams(name, price, description, username));
    }

    @And("I want to check that the product request still exist")
    public void iWantToCheckThatTheProductRequestStillExist() {
        Assert.assertNotEquals(0, prodRequestRepository.count());
    }

    @Then("The product request is not found")
    public void theProductRequestIsNotFound() {
        Assertions.assertEquals(NotFoundException.class, e.getClass());
    }
}

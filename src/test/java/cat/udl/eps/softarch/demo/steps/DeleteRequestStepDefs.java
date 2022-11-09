package cat.udl.eps.softarch.demo.steps;

import static org.hamcrest.Matchers.is;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import cat.udl.eps.softarch.demo.domain.Offer;
import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.exception.NotFoundException;
import cat.udl.eps.softarch.demo.repository.OfferRepository;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeleteRequestStepDefs {

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private UserRepository userRepository;

    @When("I delete a request with id {string}")
    public void iDeleteARequestWithId(String id) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/requests/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @Then("I want to check that the request doesn't exist anymore")
    public void iWantToCheckThatTheRequestDoesnTExistAnymore() {
        Assert.assertEquals(0, requestRepository.count());
    }

    @And("I want to check that the request still exist")
    public void iWantToCheckThatTheRequestStillExist() {
        Assert.assertNotEquals(0, requestRepository.count());
    }

    @And("There is an offer already created")
    public void thereIsAnOfferAlreadyCreated() {
        Offer offer = new Offer();
        offer.setName("croqueta2");
        offer.setPrice(new BigDecimal(100));
        offer.setDescription("le hago la competencia a la mama");
        User offerer = new User();
        offerer.setUsername("Paco");
        offerer.setPassword("password");
        offerer.setEmail("Paco" + "@gmail.com");
        offer.setOfferer(offerer);
        userRepository.save(offerer);

        offerRepository.save(offer);
        Assert.assertEquals(1, offerRepository.count());
    }

    @And("There is a request already created")
    public void thereIsARequestAlreadyCreated() {
        Request request = new Request();
        request.setName("croqueta2");
        request.setPrice(new BigDecimal(100));
        request.setDescription("le hago la competencia a la mama");
        User requester = new User();
        requester.setUsername("manolo");
        requester.setPassword("password");
        requester.setEmail("manolo" + "@gmail.com");
        request.setRequester(requester);

        request.setRequester(requester);
        requestRepository.save(request);
        Assert.assertEquals(1, requestRepository.count());
    }

    private User getUser(String username) {
        Optional<User> users = userRepository.findById(username);
        if(users.isPresent()) {
            return users.get();
        }
        throw new NotFoundException();
    }

    private Request getRequestByParams(String name, int price, String description, String username) {
        BigDecimal requestPrice = new BigDecimal(price);
        User requester = getUser(username);

        List<Request> requestList = requestRepository.findByNameAndPriceAndDescriptionAndRequester(name, requestPrice, description, requester);
        if (requestList.isEmpty()) {
            throw new NotFoundException();
        }
        return requestList.get(0);

    }

    @When("I delete a request with name {string}, price {int}, description {string} by {string}")
    public void iDeleteARequestWithNamePriceDescriptionBy(String name, int price, String description, String username) throws Exception {

  //      Assertions.assertThrows(NotFoundException.class, () -> {
            // getRequestByParams(name, price, description, username);
            Long requestId = getRequestByParams(name, price, description, username).getId();

            stepDefs.result = stepDefs.mockMvc.perform(
                            delete("/requests/" + requestId)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .with(AuthenticationStepDefs.authenticate()))
                    .andDo(print());
//        } );


    }

    @And("I want to check that the request with name {string}, price {int}, description {string} by {string} doesn't exist")
    public void iWantToCheckThatTheRequestWithNamePriceDescriptionByDoesnTExist(String name, int price, String description, String username) throws Exception {

        Assertions.assertThrows(NotFoundException.class, () -> {
            getRequestByParams(name, price, description, username);

            //stepDefs.result = stepDefs.mockMvc.perform(
              //              get("/requests/" + requestId)
                //                    .accept(MediaType.APPLICATION_JSON)
                    //                .with(AuthenticationStepDefs.authenticate()))
                  //  .andDo(print())
            //;

        });




    }

    private String getCurrentUsername() {
        return AuthenticationStepDefs.currentUsername;
    }

    @When("I delete my own created requests")
    public void iDeleteMyOwnCreatedRequests() throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/requests", getCurrentUsername())
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
                        .queryParam("username", getCurrentUsername())

        ).andDo(print())
        ;
    }

    @When("I delete requests from user {string}")
    public void iDeleteRequestsFromUser(String username) throws Exception {
        User otherUser = getUser(username);
        //Todo: cambiar para cuando el usuario no exista
        stepDefs.result = stepDefs.mockMvc.perform(
                delete("/requests", username)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())
                        .queryParam("username", username)

        ).andDo(print())
        ;
    }

    @When("I try to delete a request with name {string}, price {int}, description {string} by {string} but I can't")
    public void iTryToDeleteARequestWithNamePriceDescriptionByButICanT(String name, int price, String description, String username) {
        Assertions.assertThrows(NotFoundException.class, () -> {
            getRequestByParams(name, price, description, username);
        });
    }
}

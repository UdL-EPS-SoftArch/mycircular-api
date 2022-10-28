package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Offer;
import cat.udl.eps.softarch.demo.domain.Request;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.OfferRepository;
import cat.udl.eps.softarch.demo.repository.RequestRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.MediaType;


import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RegisterRequestStepDefs {

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationStepDefs authenticationStepDefs;

    @Transactional
    @And("There is an offer created")
    public void thereIsAnOfferCreated() throws Exception {
        Offer offer = new Offer();
        offer.setName("croqueta");
        offer.setPrice(new BigDecimal(50));
        offer.setDescription("las croquestas mas ricas de la mama");
/*
        Optional<User> users = userRepository.findById("mama");
        if(users.isPresent()) {
            User mama = users.get();
            offer.setOffererUser(mama);
        }
*/

        User mama = new User();
        mama.setEmail("mama@cocina.casa");
        mama.setUsername("mama");
        mama.setPassword("password");


        //   offerRepository.save(offer);

        // Pruebas random
        long num = requestRepository.count();
        boolean thereAreOffers = num > 0;

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(offer))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());

        //Assert.assertTrue(thereAreOffers);
        Assert.assertEquals(1, offerRepository.count());
    }


    @When("I Create a new request")
    public void iCreateANewRequest() throws Exception {
        Request request = new Request();

        request.setName("croqueta");
        request.setPrice(new BigDecimal(50));
        request.setDescription("las croquestas mas ricas de la mama");

        User nene = new User();
        nene.setEmail("nene@cocina.casa");
        nene.setUsername("nene");
        nene.setPassword("password");

        request.setRequester(nene);

        //requestRepository.save(request);

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/requests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
                //.andExpect(status().isUnauthorized());


    }

    @Then("There is a request created")
    public void thereIsARequestCreated() {
        Assert.assertEquals(1, requestRepository.count());
    }


    @And("There is an offer created with name {string}, price {int}, description {string} and offerer named {string}")
    public void thereIsAnOfferCreatedWithNamePriceDescriptionAndOffererNamed(String name, int price, String description, String offererName) throws Exception {
        Offer offer = new Offer();
        offer.setName(name);
        offer.setPrice(new BigDecimal(price));
        offer.setDescription(description);
        User offerer = new User();
        offerer.setUsername(offererName);
        offerer.setPassword("password");
        offerer.setEmail(offererName + "@gmail.com");
        offer.setOfferer(offerer);

        offerRepository.save(offer);
        Assert.assertEquals(1, offerRepository.count());
    }

    @And("There are {int} offer created")
    public void thereAreOfferCreated(int offersNum) {
        Assert.assertEquals(offersNum, offerRepository.count());
    }

    @Then("There are {int} request created")
    public void thereAreRequestCreated(int requestsNum) {
        Assert.assertEquals(requestsNum, requestRepository.count());
    }

    @And("There is a request created with name {string}, price {int}, description {string} by {string}")
    public void thereIsARequestCreatedWithNamePriceDescriptionBy(String name, int price, String description, String requesterName) throws Exception {
        Request request = new Request();
        request.setName(name);
        request.setPrice(new BigDecimal(price));
        request.setDescription(description);

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

        request.setRequester(requester);

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/requests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print())
                .andExpect(status().isCreated());

    }

    private Request setRequestParams(String name, int price, String description) {
        Request request = new Request();
        request.setName(name);
        request.setPrice(new BigDecimal(price));
        request.setDescription(description);

        String currentUsername = getCurrentUsername();
        User requester = getCurrentUser(currentUsername);
        request.setRequester(requester);

        return request;
    }

    private User getCurrentUser(String username) {
        Optional<User> users = userRepository.findById(username);
        return users.orElseGet(User::new);
    }

    private String getCurrentUsername() {
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //return authentication.getName();

        return AuthenticationStepDefs.currentUsername;
    }

    @When("I Create a new request with name {string}, price {int}, description {string}")
    public void iCreateANewRequestWithNamePriceDescription(String name, int price, String description) throws Exception {
        Request request = setRequestParams(name, price, description);


        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/requests")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print());
//                .andExpect(status().isUnauthorized());

//        requestRepository.save(request);


    }
}

package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Offer;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.OfferRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

public class OfferStepDefs {
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    private Offer offer;


    @Given("There is no offer created from the user {string}")
    public void thereIsNoOfferCreatedFromTheUser(String username) {
        Optional<User> user = userRepository.findById(username);
        Assert.assertTrue("This user has no offer created", user.isEmpty() ||
                !offerRepository.existsOfferByOfferer(user.get()));
    }

    @Then("The offer should be created together with the announcement")
    public void theOfferShouldBeCreatedTogetherWithTheAnnouncement() {
        offer = new Offer();

        Assert.assertNotNull(offer);
    }

    @And("which has a name {string}, description {string} and a price {string}.")
    public void whichHasANameDescriptionAndAPrice(String name, String description, String price) {
        offer.setName(name);
        offer.setDescription(description);
        offer.setPrice(new BigDecimal(price));

        Assert.assertEquals(name, offer.getName());
        Assert.assertEquals(description, offer.getDescription());
        Assert.assertEquals(price, String.valueOf(offer.getPrice()));
    }

    @Transactional
    @And("a ZoneDateTime {string} and a offerer user {string}")
    public void aZoneDateTimeAndAOffererUser(String dateTime, String offerer) throws Throwable {
        ZonedDateTime date = ZonedDateTime.parse(dateTime);
        offer.setDateTime(date);
        String exprectedDateStr = "2018-02-12T12:08:23Z";
        ZonedDateTime expectedDate = ZonedDateTime.parse(exprectedDateStr);
        Optional<User> user = userRepository.findById(offerer);
        user.ifPresent(value -> offer.setOfferer(value));

        Assert.assertEquals(expectedDate, offer.getDateTime());
        user.ifPresent(value -> Assert.assertEquals(value, offer.getOfferer()));
        System.out.println(offer.getDateTime());

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/offers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(offer))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("After all the steps I can retrieve this offer which should have the name {string}.")
    public void afterAllTheStepsICanRetrieveThisOfferWhichShouldHaveTheName(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/offers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(status().isOk());
    }



    @Then("I want to modify this product's name to {string}.")
    public void iWantToModifyThisProductSNameTo(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/offers/{id}", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((new JSONObject().put("name", name)).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print())
                .andExpect(jsonPath("$.name", is(name)));
    }

    @Then("I want to delete the offer with id {string}.")
    public void iWantToDeleteTheOfferWithId(String id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/offers/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("I want to check that the offer doesn't exist anymore.")
    public void iWantToCheckThatTheOfferDoesnTExistAnymore() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/offers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

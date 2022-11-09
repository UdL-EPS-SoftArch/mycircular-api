package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.*;
import cat.udl.eps.softarch.demo.repository.OfferRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;
import io.cucumber.java.en.Then;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.MediaType;
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

public class CounterOfferStepDefs {
    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;

    private CounterOffer counterOffer;

    @Then("The Counter offer should be created.")
    public void theProductOfferShouldBeCreated() {
        counterOffer = new CounterOffer();

        Assert.assertNotNull(counterOffer);
    }

    @And("Counter offer has a name {string}, description {string} and a price {string}.")
    public void counterOfferHasANameDescriptionAndAPrice(String name, String description, String price) {
        counterOffer.setName(name);
        counterOffer.setDescription(description);
        counterOffer.setPrice(new BigDecimal(price));

        Assert.assertEquals(name, counterOffer.getName());
        Assert.assertEquals(description, counterOffer.getDescription());
        Assert.assertEquals(price, String.valueOf(counterOffer.getPrice()));
    }


    @And("counter offer has ZoneDateTime {string} and a offerer user {string}")
    public void counterOfferHasZoneDateTimeAndAOffererUser(String dateTime, String offerer) {
        ZonedDateTime date = ZonedDateTime.parse(dateTime);
        counterOffer.setDateTime(date);
        String exprectedDateStr = "2018-02-12T12:08:23Z";
        ZonedDateTime expectedDate = ZonedDateTime.parse(exprectedDateStr);
        Optional<User> user = userRepository.findById(offerer);
        user.ifPresent(value -> counterOffer.setOfferer(value));

        Assert.assertEquals(expectedDate, counterOffer.getDateTime());
        user.ifPresent(value -> Assert.assertEquals(value, counterOffer.getOfferer()));
    }

    @And("the counter offer price is {string} tokens.")
    public void theCounterOfferPriceIsTokens(String price) throws Throwable {
        counterOffer.setCounterOfferPrice(new BigDecimal(price));

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/counterOffers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(counterOffer))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }


    @And("After all the steps I can retrieve this counter offer which should have the counter offer price of {string}.")
    public void afterAllTheStepsICanRetrieveThisCounterOfferWhichShouldHaveTheCounterOfferPriceOf(
            String counterOfferPrice) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/counterOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                //The database has a BigDecimal but the JSON for some reason interprets the counterOfferPrice
                //as a Double. That's why we needed to cast to double.
                .andExpect(jsonPath("$.counterOfferPrice", is(new Double(counterOfferPrice))))
                .andExpect(status().isOk());
    }

    @Then("I want to modify this Counter offer product name to {string}.")
    public void iWantToModifyThisCounterOfferProductNameTo(String name) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/counterOffers/{id}", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((new JSONObject().put("name", name)).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print())
                .andExpect(jsonPath("$.name", is(name)));
    }

    @And("After all the steps I can retrieve this counter offer which should have the counter offer name of {string}.")
    public void afterAllTheStepsICanRetrieveThisCounterOfferWhichShouldHaveTheCounterOfferNameOf(String name)
            throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/counterOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(status().isOk());
    }

    @Then("I want to delete the Counter offer with id {string}.")
    public void iWantToDeleteTheCounterOfferWithId(String id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/counterOffers/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("I want to check that the Counter offer doesn't exist anymore.")
    public void iWantToCheckThatTheCounterOfferDoesnTExistAnymore() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/counterOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Then("I shouldn't be able to create any Counter offer.")
    public void iShouldnTBeAbleToCreateAnyCounterOffer() throws Throwable {
        CounterOffer newCounterOffer = new CounterOffer();

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/counterOffers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(newCounterOffer))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Then("I shouldn't be able to modify any counter offer.")
    public void iShouldnTBeAbleToModifyAnyCounterOffer() throws Throwable {
        String name = "test";

        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/counterOffers/{id}", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((new JSONObject().put("name", name)).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Then("I shouldn't be able to delete any Counter offer.")
    public void iShouldnTBeAbleToDeleteAnyCounterOffer() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/counterOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}

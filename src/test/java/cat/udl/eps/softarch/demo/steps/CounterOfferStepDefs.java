package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.*;
import cat.udl.eps.softarch.demo.repository.OfferRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import org.springframework.beans.factory.annotation.Autowired;

import cat.udl.eps.softarch.demo.domain.Offer;
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

    @And("After all the steps I can retrieve this counter offer which should have the name {string}.")
    public void afterAllTheStepsICanRetrieveThisCounterOfferWhichShouldHaveTheName(String counterOfferPrice) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/counterOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.counterOfferPrice", is(counterOfferPrice)))
                .andExpect(status().isOk());
    }

}

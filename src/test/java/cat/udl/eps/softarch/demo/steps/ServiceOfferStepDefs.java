package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.ServiceOffer;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.OfferRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
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

public class ServiceOfferStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository userRepository;

    private ServiceOffer offer;

    @Then("The Service offer should be created")
    public void theServiceOfferShouldBeCreated() {
        offer = new ServiceOffer();
        Assert.assertNotNull(offer);
    }

    @And("the Service Offer has a name {string}, description {string} and a price {string}.")
    public void theServiceOfferHasANameDescriptionAndAPrice(String name, String description, String price) {
        offer.setName(name);
        offer.setDescription(description);
        offer.setPrice(new BigDecimal(price));

        Assert.assertEquals(name, offer.getName());
        Assert.assertEquals(description, offer.getDescription());
        Assert.assertEquals(price, String.valueOf(offer.getPrice()));
    }

    @And("the Service Offer has a ZoneDateTime {string} and a offerer user {string}")
    public void theServiceOfferHasAZoneDateTimeAndAOffererUser(String dateTime, String offerer) {
        ZonedDateTime date = ZonedDateTime.parse(dateTime);
        offer.setDateTime(date);
        String exprectedDateStr = "2018-02-12T12:08:23Z";
        ZonedDateTime expectedDate = ZonedDateTime.parse(exprectedDateStr);
        Optional<User> user = userRepository.findById(offerer);
        user.ifPresent(value -> offer.setOfferer(value));

        Assert.assertEquals(expectedDate, offer.getDateTime());
        user.ifPresent(value -> Assert.assertEquals(value, offer.getOfferer()));
        System.out.println(offer.getDateTime());


    }

    @And("service are available {string} and duration is {string} hours.")
    public void serviceAreAvailableAndDurationIsHours(String avaliable, String time) throws Throwable{
        offer.setAvailability(Boolean.parseBoolean(avaliable));
        offer.setDurationInHours(Integer.parseInt(time));
        Assert.assertEquals(Boolean.parseBoolean(avaliable), offer.getAvailability());
        Assert.assertEquals(Integer.parseInt(time), offer.getDurationInHours());

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/serviceOffers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(offer))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("After all the steps I can retrieve this Service offer which should have the available {string}.")
    public void afterAllTheStepsICanRetrieveThisServiceOfferWhichShouldHaveTheAvailable(String available) throws Throwable  {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/serviceOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.availability", is(Boolean.parseBoolean(available))))
                .andExpect(status().isOk());
    }


    @Then("I want to modify this Service Offer duration to {string} hours.")
    public void iWantToModifyThisServiceOfferDurationToHours(String time) throws Throwable  {
        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/serviceOffers/{id}", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((new JSONObject().put("durationInHours", Integer.parseInt(time))).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print())
                .andExpect(jsonPath("$.durationInHours", is(Integer.parseInt(time))));
    }

    @And("After all the steps I can retrieve this Service Offer which should have a duration off {string} hours.")
    public void afterAllTheStepsICanRetrieveThisServiceOfferWhichShouldHaveADurationOffHours(String time) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/serviceOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.durationInHours", is(Integer.parseInt(time))))
                .andExpect(status().isOk());
    }

    @Then("i want to delete the Service Offer with id {string}")
    public void iWantToDeleteTheServiceOfferWithId(String id) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/serviceOffers/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("i want to check that the Service Offer doesn't exist anymore.")
    public void iWantToCheckThatTheServiceOfferDoesnTExistAnymore() throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/serviceOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}

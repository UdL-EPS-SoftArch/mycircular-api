package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Offer;
import cat.udl.eps.softarch.demo.domain.ServiceOffer;
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
import java.util.Date;
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

    @Autowired
    private OfferRepository offerRepository;
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
        Date expectedDate = new Date("01/02/2018");
        offer.setDateTime(new Date(dateTime));
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


}

package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.ProductOffer;
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

public class ProductOfferStepDefs {

    @Autowired
    private StepDefs stepDefs;

    @Autowired
    private UserRepository userRepository;

    private ProductOffer productOffer;
    
    @Then("The product offer should be created.")
    public void theProductOfferShouldBeCreated() {
        productOffer = new ProductOffer();

        Assert.assertNotNull(productOffer);
    }

    @And("the product offer has a name {string}, description {string} and a price {string}.")
    public void theProductOfferHasANameDescriptionAndAPrice(String name, String description, String price) {
        productOffer.setName(name);
        productOffer.setDescription(description);
        productOffer.setPrice(new BigDecimal(price));

        Assert.assertEquals(name, productOffer.getName());
        Assert.assertEquals(description, productOffer.getDescription());
        Assert.assertEquals(price, String.valueOf(productOffer.getPrice()));
    }

    @And("the product offer has a ZoneDateTime {string} and a offerer user {string}")
    public void theProductOfferHasAZoneDateTimeAndAOffererUser(String dateTime, String offerer) {
        ZonedDateTime date = ZonedDateTime.parse(dateTime);
        productOffer.setDateTime(date);
        String exprectedDateStr = "2018-02-12T12:08:23Z";
        ZonedDateTime expectedDate = ZonedDateTime.parse(exprectedDateStr);
        Optional<User> user = userRepository.findById(offerer);
        user.ifPresent(value -> productOffer.setOfferer(value));

        Assert.assertEquals(expectedDate, productOffer.getDateTime());
        user.ifPresent(value -> Assert.assertEquals(value, productOffer.getOfferer()));
    }

    @And("a manufacturer {string} a band {string} and a product code {string}")
    public void aManufacturerABandAndAProductCode(String manufacturer, String brand, String productCode)
            throws Throwable {
        productOffer.setManufacturer(manufacturer);
        productOffer.setBrand(brand);
        productOffer.setProductCode(productCode);

        Assert.assertEquals(manufacturer, productOffer.getManufacturer());
        Assert.assertEquals(brand, productOffer.getBrand());
        Assert.assertEquals(productCode, productOffer.getProductCode());

        stepDefs.result = stepDefs.mockMvc.perform(
                        post("/productOffers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(stepDefs.mapper.writeValueAsString(productOffer))
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("After all the steps I can retrieve this product offer which should have the product code {string}.")
    public void afterAllTheStepsICanRetrieveThisProductOfferWhichShouldHaveTheProductCode(String productCode)
            throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/productOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.productCode", is(productCode)))
                .andExpect(status().isOk());
    }

    @Then("I want to modify this product's brand to {string}.")
    public void iWantToModifyThisProductSBrandTo(String brandName) throws Throwable {
        stepDefs.result = stepDefs.mockMvc.perform(
                        patch("/productOffers/{id}", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content((new JSONObject().put("brand", brandName)).toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate())
                ).andDo(print())
                .andExpect(jsonPath("$.brand", is(brandName)));
    }

    @And("After all the steps I can retrieve this product offer which should have the brand {string}.")
    public void afterAllTheStepsICanRetrieveThisProductOfferWhichShouldHaveTheBrand(String brandName) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/productOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.brand", is(brandName)))
                .andExpect(status().isOk());
    }


    @Then("I want to delete the product offer with id {string}.")
    public void iWantToDeleteTheProductOfferWithId(String id) throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        delete("/productOffers/{id}", id)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print());
    }

    @And("I want to check that the product offer doesn't exist anymore.")
    public void iWantToCheckThatTheProductOfferDoesnTExistAnymore() throws Throwable{
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/productOffers/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


}

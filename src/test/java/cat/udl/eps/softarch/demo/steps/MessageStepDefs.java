package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.*;
import cat.udl.eps.softarch.demo.repository.AnnouncementRepository;
import cat.udl.eps.softarch.demo.repository.MessageRepository;
import cat.udl.eps.softarch.demo.repository.ProductOfferRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class MessageStepDefs {

    @Autowired
    private StepDefs stepDefs;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;


    @And("I don't have any messages")
    public void iDonTHaveAnyMessages() {
        long messages = messageRepository.count();
        assert messages == 0;
    }


    @When("I send a message with date {string}, text {string}")
    public void iSendAMessageWithDateText(String date, String text) throws Exception{
        ZonedDateTime dated = ZonedDateTime.parse(date);
        Message message = new Message();


        //message.setId(ident);
        message.setWhen(dated);
        message.setText(text);


        stepDefs.result = stepDefs.mockMvc.perform(
                post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(message))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())).andDo(print());
    }


    //Comprovar que missatge ha agafat el Product Offer a la seua taula
    @And("The Message is associated with the Product Offer {string}")
    public void theMessageIsAssociatedWithTheProductOffer(String  product) throws Exception {
        String ids = stepDefs.result.andReturn().getResponse().getHeader("Location");
        assert ids != null;
        stepDefs.result = stepDefs.mockMvc.perform(
                        get(ids)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(status().isOk());
        JSONObject response = new JSONObject(stepDefs.result.andReturn().getResponse().getContentAsString());
        String productByHref = response.getJSONObject("_links").getJSONObject("product").getString("href");
        assertProvidedByEqualsToExpectedProduct(productByHref,product);

    }

    private void assertProvidedByEqualsToExpectedProduct(String productByHref, String product) throws Exception{
        stepDefs.mockMvc.perform(
          get(productByHref)
                  .accept(MediaType.APPLICATION_JSON)
                  .with(AuthenticationStepDefs.authenticate())
        )
                .andDo(print())
                .andExpect(jsonPath("$.name", is(product)));
    }

    @When("I send a message with date {string}, text {string} and for {string}")
    public void iSendAMessageWithDateTextAndFor(String date, String text, String product) throws Exception {
        ZonedDateTime dated = ZonedDateTime.parse(date);
        Message message = new Message();
        List<Announcement> offer = announcementRepository.findByName(product);


        //message.setId(ident);
        message.setWhen(dated);
        message.setText(text);
        message.setProduct(offer.get(0));


        stepDefs.result = stepDefs.mockMvc.perform(
                post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(message))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())).andDo(print());

    }


}

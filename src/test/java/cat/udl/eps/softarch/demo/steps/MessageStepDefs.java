package cat.udl.eps.softarch.demo.steps;

import cat.udl.eps.softarch.demo.domain.Announcement;
import cat.udl.eps.softarch.demo.domain.Message;
import cat.udl.eps.softarch.demo.domain.Offer;
import cat.udl.eps.softarch.demo.domain.User;
import cat.udl.eps.softarch.demo.repository.AnnouncementRepository;
import cat.udl.eps.softarch.demo.repository.MessageRepository;
import cat.udl.eps.softarch.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
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

    /*@When("I send a message with date {string}, text {string} and author {string}")
    public void iSendAMessageWithDateTextAndAuthor(String date, String text, String product) throws Exception{
        ZonedDateTime dated = ZonedDateTime.parse(date);
        Message message = new Message();
        List<Announcement> product = announcementRepository.findById(Long.parseLong(product));

        //message.setId(ident);
        message.setWhen(dated);
        message.setText(text);
        message.setProduct(product.get(0));

        stepDefs.result = stepDefs.mockMvc.perform(
                post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(message))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())).andDo(print());
    }*/

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


    @And("The Message is associated with the Product Offer {long}")
    public void theMessageIsAssociatedWithTheProductOffer(Long id) throws Exception {
        stepDefs.result = stepDefs.mockMvc.perform(
                        get("/messages/{id}", "1")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(AuthenticationStepDefs.authenticate()))
                .andDo(print())
                .andExpect(jsonPath("$.product", is(id)))
                .andExpect(status().isOk());

    }

    @When("I send a message with date {string}, text {string} and for {long}")
    public void iSendAMessageWithDateTextAndFor(String date, String text, Long product) throws Exception {
        ZonedDateTime dated = ZonedDateTime.parse(date);
        Message message = new Message();
        Optional<Announcement> offer = announcementRepository.findById(product);


        //message.setId(ident);
        message.setWhen(dated);
        message.setText(text);
        offer.ifPresent(message::setProduct);


        stepDefs.result = stepDefs.mockMvc.perform(
                post("/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(stepDefs.mapper.writeValueAsString(message))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(AuthenticationStepDefs.authenticate())).andDo(print());

    }


}
